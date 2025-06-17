package com.qn_org.backend.models.enums;

import lombok.Getter;

@Getter
public enum MemberRole {

    UNDEFINED(0),
    ADMIN(1),
    NORMAL_MEMBER(2);
    private final int value;
    MemberRole(int value){
        this.value = value;
    }

    public static MemberRole fromValue(int value) {
        for(MemberRole role : MemberRole.values()){
            if(role.getValue() == value)
                return role;
        }
        return UNDEFINED;
    }

    public static boolean isAdmin(int roleLevel) {
        return roleLevel == 2;
    }
}
