package com.qn_org.backend.models;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@Entity
@RequiredArgsConstructor
@Table(name = "USER")
public class User implements UserDetails {

    @Id
    @Column(name = "USER_ID", nullable = false, unique = true)
    private String userId;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "EMAIL_ADDRESS", nullable = false)
    private String emailAddress;

    @Column(name = "USER_INFO_KEY", nullable = false)
    private String userInfoKey;

    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @Column(name = "USER_TYPE", nullable = false)
    private int userType;

    @Column(name = "USER_AVATAR")
    private String userAvatar;

    @Column(name = "INS_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate;

    @Column(name = "DEL_FLG", nullable = false)
    private boolean delFlg = false;

    @Column(name = "PASSWORD_VALIDATED_FLG", nullable = false)
    private boolean passwordValidatedFlg = false;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return !this.delFlg;
    }

}
