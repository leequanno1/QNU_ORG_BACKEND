package com.qn_org.backend.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, java.io.IOException {
        if (authException instanceof InsufficientAuthenticationException) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setHeader("Content-Type","application/json");
            response.getWriter().write(String.format("{ \"status\": %d, \"msg\": \"Jwt token is expired\" }", HttpStatus.FORBIDDEN.value()));
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }
}

