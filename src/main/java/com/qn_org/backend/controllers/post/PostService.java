package com.qn_org.backend.controllers.post;

import com.qn_org.backend.common_requests.FromToIndexRequest;
import com.qn_org.backend.config.JwtService;
import com.qn_org.backend.models.Member;
import com.qn_org.backend.models.Post;
import com.qn_org.backend.models.User;
import com.qn_org.backend.models.enums.MemberRole;
import com.qn_org.backend.repositories.MemberRepository;
import com.qn_org.backend.repositories.PostRepository;
import com.qn_org.backend.repositories.UserRepository;
import com.qn_org.backend.services.exceptions.ApprovalNoAuthorityException;
import com.qn_org.backend.services.exceptions.EditorNoAuthorityException;
import com.qn_org.backend.services.exceptions.NoAuthorityToDoActionException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Post create(CreatePostRequest request) {
        Member member = memberRepository.getReferenceById(request.getPosterId());
        boolean isApproved = member.getRoleLevel() == MemberRole.ADMIN.getValue();
        Post post = Post.builder()
                .postId("POS_" + UUID.randomUUID())
                .poster(member)
                .postTitle(request.getPostTitle())
                .postContent(request.getPostContent())
                .comments(0)
                .insDate(new Date())
                .isApproved(isApproved)
                .build();
        repository.save(post);
        return post;
    }

    public Post approve(ApprovePostRequest request) throws ApprovalNoAuthorityException {
        Member member = memberRepository.getReferenceById(request.getApprovalId());
        if(!(member.getRoleLevel() == MemberRole.ADMIN.getValue()))
            throw new ApprovalNoAuthorityException();
        Post post = repository.getReferenceById(request.getPostId());
        post.setApproved(true);
        repository.save(post);
        // TODO: Handle add notification here.
        return post;
    }

    public Post edit(EditPostRequest request) throws EditorNoAuthorityException {
        Post post = repository.getReferenceById(request.getPostId());
        if(!post.getPoster().getMemberId().equals(request.getPosterId())) {
            throw new EditorNoAuthorityException();
        }
        post.setPostTitle(request.getPostTitle());
        post.setPostContent(request.getPostContent());
        repository.save(post);
        return post;
    }

    public Post delete(DeletePostRequest request) throws NoAuthorityToDoActionException {
        Post post = repository.getReferenceById(request.getPostId());
        boolean isDeleted = false;
        Member member = memberRepository.getReferenceById(request.getPosterId());
        User user = userRepository.getReferenceById(member.getUserId());
        if(user.isSuperAdmin())
            isDeleted = true;
        if(member.getMemberId().equals(request.getPostId()))
            isDeleted = true;
        if(isDeleted) {
            post.setDelFlg(true);
            repository.save(post);
            return post;
        } else {
            throw new NoAuthorityToDoActionException();
        }
    }

    public List<Post> getAll(FromToIndexRequest request) {
        if(!request.isValid()) {
            return new ArrayList<>();
        }
        return repository.getAll(request.getLimit(), request.getOffset());
    }

    public Post getById(String postId, HttpServletRequest request) {
        String authorHeader = request.getHeader("Authorization");
        String token = authorHeader.substring(7);
        String userId = jwtService.extractUsername(token);
        Post post = repository.getReferenceById(postId);
        String orgId = post.getOrgId();
        String memberId = memberRepository.getMemberIdByUserIdAndOrgId(userId, orgId);
        return memberId != null && !memberId.isBlank() && post.isApproved()?post:null;
    }

    public List<Post> getInOrg(GetInOrgRequest request) {
        if(!request.getOffset().isValid()) {
            return new ArrayList<>();
        }
        return repository.getInOrg(request.getOrgId(),request.getOffset().getLimit(), request.getOffset().getOffset());
    }

    public List<Post> getNotApprovedInOrg(GetInOrgRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        String authorHeader = servletRequest.getHeader("Authorization");
        String token = authorHeader.substring(7);
        String userId = jwtService.extractUsername(token);
        String adminId = memberRepository.getAdminIdByUserIdAndOrgId(userId, request.getOrgId());
        if(adminId == null || adminId.isBlank())
            throw new NoAuthorityToDoActionException();
        if(!request.getOffset().isValid()) {
            return new ArrayList<>();
        }
        return repository.getNotApprovedInOrg(request.getOrgId(),request.getOffset().getLimit(), request.getOffset().getOffset());
    }
}
