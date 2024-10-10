package com.qn_org.backend.models;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@Entity
@RequiredArgsConstructor
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
    private Date insDate = new Date();

    @Column(name = "DEL_FLG", nullable = false)
    private boolean delFlg = false;

    @Column(name = "IS_APPROVED", nullable = false)
    private boolean isApproved = false;

}

