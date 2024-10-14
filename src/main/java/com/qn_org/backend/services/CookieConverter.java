package com.qn_org.backend.services;

import jakarta.servlet.http.Cookie;

public class CookieConverter {
    public static String convertCookieToString(Cookie cookie) {
        StringBuilder cookieString = new StringBuilder();

        // Basic cookie format: name=value
        cookieString.append(cookie.getName()).append("=").append(cookie.getValue());

        // Add optional cookie attributes
        if (cookie.getPath() != null) {
            cookieString.append("; Path=").append(cookie.getPath());
        }

        if (cookie.getDomain() != null) {
            cookieString.append("; Domain=").append(cookie.getDomain());
        }

        if (cookie.getMaxAge() > 0) {
            cookieString.append("; Max-Age=").append(cookie.getMaxAge());
        }

        if (cookie.isHttpOnly()) {
            cookieString.append("; HttpOnly");
        }

        if (cookie.getSecure()) {
            cookieString.append("; Secure");
        }

        return cookieString.toString();
    }
}
