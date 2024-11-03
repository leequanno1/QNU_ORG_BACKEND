package com.qn_org.backend.controllers.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qn_org.backend.models.Image;
import com.qn_org.backend.models.Post;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostDTO {
    private String postId;

    private String posterId;

    private String postTitle;

    private String postContent;

    private int comments;

    private Date insDate;

    private boolean delFlg;

    private boolean isApproved;

    private String orgId;

    private List<Image> images;

    public PostDTO(Post post, List<Image> images) {
        postId = post.getPostId();
        posterId = post.getPoster().getMemberId();
        postTitle = post.getPostTitle();
        postContent = post.getPostContent();
        comments = post.getComments();
        insDate = post.getInsDate();
        delFlg = post.isDelFlg();
        isApproved = post.isApproved();
        this.images = images;
    }

    public PostDTO(Post post) {
        postId = post.getPostId();
        posterId = post.getPoster().getMemberId();
        postTitle = post.getPostTitle();
        postContent = post.getPostContent();
        comments = post.getComments();
        insDate = post.getInsDate();
        delFlg = post.isDelFlg();
        isApproved = post.isApproved();
    }

    public static List<PostDTO> formPosts(List<Post> posts) {
        List<PostDTO> postDTOS = new ArrayList<>();
        for(Post post: posts) {
            postDTOS.add(new PostDTO(post));
        }
        return postDTOS;
    }
}
