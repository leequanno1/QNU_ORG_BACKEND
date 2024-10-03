package com.qn_org.backend.models;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "POST_NOTIFICATION")
public class PostNotification {

    @Id
    @Column(name = "POST_NOTI_ID", nullable = false, unique = true)
    private String postNotiId;

    @Column(name = "POSTER_AVT")
    private String posterAvt;

    @Column(name = "POSTER_NAME", nullable = false)
    private String posterName;

    @ManyToOne
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;

    @Column(name = "INS_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate;

    @Column(name = "DEL_FLG", nullable = false)
    private boolean delFlg = false;

    public String getPostNotiId() {
        return postNotiId;
    }

    public String getPosterAvt() {
        return posterAvt;
    }

    public String getPosterName() {
        return posterName;
    }

    public Post getPost() {
        return post;
    }

    public Date getInsDate() {
        return insDate;
    }

    public boolean isDelFlg() {
        return delFlg;
    }

    public void setPostNotiId(String postNotiId) {
        this.postNotiId = postNotiId;
    }

    public void setPosterAvt(String posterAvt) {
        this.posterAvt = posterAvt;
    }

    public void setPosterName(String posterName) {
        this.posterName = posterName;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setInsDate(Date insDate) {
        this.insDate = insDate;
    }

    public void setDelFlg(boolean delFlg) {
        this.delFlg = delFlg;
    }
}
