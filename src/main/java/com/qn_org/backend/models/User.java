package com.qn_org.backend.models;
import com.google.gson.Gson;
import com.qn_org.backend.services.JsonUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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

    @Column(name = "USER_BACKGROUND")
    private String userBackground;

    @Column(name = "INS_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate = new Date();

    @Column(name = "DEL_FLG", nullable = false)
    private boolean delFlg = false;

    @Column(name = "PASSWORD_VALIDATED_FLG", nullable = false)
    private boolean passwordValidatedFlg = false;

    @Column(name = "IS_SUPER_ADMIN", nullable = false)
    private boolean isSuperAdmin = false;

    @Column(name = "ORG_IDS", nullable = false)
    private String orgIds = new Gson().toJson(new ArrayList<String>());

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

    public static String listToJsonString (List<String> list) {
        return JsonUtil.listToJsonString(list);
    }

    public static List<String> jsonStringToList(String jsonString) {
        return JsonUtil.jsonStringToList(jsonString);
    }
}
