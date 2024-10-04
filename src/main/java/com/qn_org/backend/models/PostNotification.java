package com.qn_org.backend.models;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@Entity
@RequiredArgsConstructor
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

}
