package com.qn_org.backend.models;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "POST")
public class Post {

    @Id
    @Column(name = "POST_ID", nullable = false, unique = true)
    private String postId;

    @ManyToOne
    @JoinColumn(name = "POSTER_ID", nullable = false)
    private Member poster;

    @Column(name = "POST_TITLE")
    private String postTitle;

    @Column(name = "POST_CONTENT")
    private String postContent;

    @Column(name = "COMMENTS", nullable = false)
    private int comments = 0;

    @Column(name = "INS_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate;

    @Column(name = "DEL_FLG", nullable = false)
    private boolean delFlg = false;

    public String getPostId() {
        return postId;
    }

    public Member getPoster() {
        return poster;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public int getComments() {
        return comments;
    }

    public Date getInsDate() {
        return insDate;
    }

    public boolean isDelFlg() {
        return delFlg;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setPoster(Member poster) {
        this.poster = poster;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public void setInsDate(Date insDate) {
        this.insDate = insDate;
    }

    public void setDelFlg(boolean delFlg) {
        this.delFlg = delFlg;
    }
}

