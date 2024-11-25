package com.qn_org.backend.controllers.post;

import com.qn_org.backend.common_requests.FromToIndexRequest;
import com.qn_org.backend.config.JwtService;
import com.qn_org.backend.controllers.image.ImageService;
import com.qn_org.backend.controllers.image.SaveImagesRequest;
import com.qn_org.backend.models.*;
import com.qn_org.backend.models.enums.MemberRole;
import com.qn_org.backend.repositories.MemberRepository;
import com.qn_org.backend.repositories.OrganizationRepository;
import com.qn_org.backend.repositories.PostRepository;
import com.qn_org.backend.repositories.UserRepository;
import com.qn_org.backend.services.exceptions.ApprovalNoAuthorityException;
import com.qn_org.backend.services.exceptions.EditorNoAuthorityException;
import com.qn_org.backend.services.exceptions.NoAuthorityToDoActionException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository repository;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final OrganizationRepository orgRepository;
    private final ImageService imageService;

    public PostDTO create(CreatePostRequest request, HttpServletRequest servletRequest) throws IOException, NoAuthorityToDoActionException {
        String userId = jwtService.extractUserId(servletRequest);
        Member member = memberRepository.getReferenceById(request.getPosterId());
        if(!member.getUserId().equals(userId)) {
            throw new NoAuthorityToDoActionException();
        }
        Organization org = member.getOrganization();
        org.setPosts(org.getPosts()+1);
        boolean isApproved = member.getRoleLevel() == 2;
        Post post = Post.builder()
                .postId("POS_" + UUID.randomUUID())
                .poster(member)
                .postTitle(request.getPostTitle())
                .postContent(request.getPostContent())
                .comments(0)
                .insDate(new Date())
                .isApproved(isApproved)
                .orgId(org.getOrgId())
                .build();
        repository.save(post);
        orgRepository.save(org);
        List<Image> images = imageService.saveImages(new SaveImagesRequest(post.getPostId(), request.getImages()));
        return new PostDTO(post, images);
    }

    public PostDTO approve(ApprovePostRequest request, HttpServletRequest servletRequest) throws ApprovalNoAuthorityException {
        Post post = repository.getReferenceById(request.getPostId());
        String userId = jwtService.extractUserId(servletRequest);
        Member member = memberRepository.getReferenceById(request.getApprovalId());
        if(!(
                member.getRoleLevel() == 2 &&
                member.getOrganization().getOrgId().equals(post.getOrgId()) &&
                member.getUserId().equals(userId)
        ))
            throw new ApprovalNoAuthorityException();
        post.setApproved(true);
        repository.save(post);
        // TODO: Handle add notification here.
        return new PostDTO(post);
    }

    public PostDTO edit(EditPostRequest request, HttpServletRequest servletRequest) throws EditorNoAuthorityException, IOException {
        String userId = jwtService.extractUserId(servletRequest);
        Member member = memberRepository.getReferenceById(request.getPosterId());
        Post post = repository.getReferenceById(request.getPostId());
        if(!userId.equals(member.getUserId()) || !post.getPoster().getMemberId().equals(request.getPosterId())) {
            throw new EditorNoAuthorityException();
        }
        post.setPostTitle(request.getPostTitle());
        post.setPostContent(request.getPostContent());
        repository.save(post);
        imageService.deleteImage(request.getDelImagesId());
        List<Image> images = imageService.saveImages(new SaveImagesRequest(post.getPostId(), request.getNewImage()));
        return new PostDTO(post, images);
    }

    public PostDTO delete(DeletePostRequest request) throws NoAuthorityToDoActionException {
        Post post = repository.getReferenceById(request.getPostId());
        boolean isDeleted = false;
        Member member = memberRepository.getReferenceById(request.getPosterId());
        User user = userRepository.getReferenceById(member.getUserId());
        if(user.isSuperAdmin())
            isDeleted = true;
        if(member.getMemberId().equals(request.getPosterId()) || member.getRoleLevel() == 2)
            isDeleted = true;
        if(isDeleted) {
            post.setDelFlg(true);
            repository.save(post);
            return new PostDTO(post);
        } else {
            throw new NoAuthorityToDoActionException();
        }
    }

    public List<PostDTO> getAll(FromToIndexRequest request, HttpServletRequest servletRequest) {
        String userId = jwtService.extractUserId(servletRequest);
        String orgIds = userRepository.getReferenceById(userId).getOrgIds();
        List<String> orgIdList = User.jsonStringToList(orgIds);
        if(!request.isValid()) {
            return new ArrayList<>();
        }
        return PostDTO.formPosts(repository.getAll(orgIdList, request.getLimit(), request.getOffset()));
    }

    public PostDTO getById(String postId, HttpServletRequest request) {
        String userId = jwtService.extractUserId(request);
        Post post = repository.getReferenceById(postId);
        String orgId = post.getOrgId();
        String memberId = memberRepository.getMemberIdByUserIdAndOrgId(userId, orgId);
        return memberId != null && !memberId.isBlank() && post.isApproved()?new PostDTO(post):null;
    }

    public List<PostDTO> getInOrg(GetInOrgRequest request) {
        if(!request.getOffset().isValid()) {
            return new ArrayList<>();
        }
        return PostDTO.formPosts(repository.getInOrg(request.getOrgId(),request.getOffset().getLimit(), request.getOffset().getOffset()));
    }

    public List<PostDTO> getNotApprovedInOrg(GetInOrgRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        String userId = jwtService.extractUserId(servletRequest);
        System.out.println(userId);
        System.out.println(request.getOrgId());
        String adminId = memberRepository.getAdminIdByUserIdAndOrgId(userId, request.getOrgId());
        if(adminId == null || adminId.isBlank())
            throw new NoAuthorityToDoActionException();
        if(!request.getOffset().isValid()) {
            return new ArrayList<>();
        }
        return PostDTO.formPosts(repository.getNotApprovedInOrg(request.getOrgId(),request.getOffset().getLimit(), request.getOffset().getOffset()));
    }
}
