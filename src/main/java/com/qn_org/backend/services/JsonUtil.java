package com.qn_org.backend.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
}
