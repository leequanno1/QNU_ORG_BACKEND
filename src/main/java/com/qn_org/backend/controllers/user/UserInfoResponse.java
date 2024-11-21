package com.qn_org.backend.controllers.user;

import com.qn_org.backend.models.User;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private String userId;
    private String emailAddress;
    private String displayName;
    private String userAvatar;
    private int userType;

    public UserInfoResponse(User user) {
        this.userId = user.getUserId();
        this.emailAddress = user.getEmailAddress();
        this.displayName = user.getDisplayName();
        this.userAvatar = user.getUserAvatar();
        this.userType = user.getUserType();
    }

}
