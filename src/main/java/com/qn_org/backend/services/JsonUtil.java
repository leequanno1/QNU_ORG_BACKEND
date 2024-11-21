package com.qn_org.backend.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qn_org.backend.models.enums.MemberRole;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    public static String listToJsonString (List<String> list) {
        return new Gson().toJson(list);
    }

    public static List<String> jsonStringToList(String jsonString) {
        Type listType = new TypeToken<List<String>>(){}.getType();
        List<String> res = new Gson().fromJson(jsonString,listType);
        return res == null ? new ArrayList<>() : res;
    }

    public static int getRoleLevel(String roleId) {
        List<String> roles = JsonUtil.jsonStringToList(roleId);
        int level = 0;
        for(String role: roles) {
            if(MemberRole.fromValue(Integer.parseInt(role)) == MemberRole.ADMIN && level < MemberRole.ADMIN.getValue()) {
                level = 2;
            }
            if(MemberRole.fromValue(Integer.parseInt(role)) == MemberRole.NORMAL_MEMBER && level < MemberRole.NORMAL_MEMBER.getValue())
                level = 1;
        }
        return level;
    }
}
