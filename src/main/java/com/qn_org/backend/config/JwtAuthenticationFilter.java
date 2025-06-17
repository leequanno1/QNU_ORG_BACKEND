package com.qn_org.backend.config;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userId;
        try {
            System.out.println(request.getRequestURI());
            if(request.getRequestURI().contains("/api/v1/auth/")
                    || request.getRequestURI().contains("/v3/api-docs")
                    || request.getRequestURI().contains("/swagger-ui")
                    || request.getRequestURI().contains("/api/image")
                    || request.getRequestURI().contains("/api/validation/send-forget-password-code")
                    || request.getRequestURI().contains("/api/validation/change_forgot_password")
                    || request.getRequestURI().contains("/favicon.ico")){
                filterChain.doFilter(request,response);
                return;
            }else if(authHeader == null || !authHeader.startsWith("Bearer")) {
                throw new AccountExpiredException("");
            }
            jwt = authHeader.substring(7);
            userId = jwtService.extractUsername(jwt);
            if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userId);
                if(jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }else {
                    throw new ExpiredJwtException(null,null,"");
                }
            }
            filterChain.doFilter(request,response);
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof ExpiredJwtException) {
                handlerExceptionResolver.resolveException(request,response,null, new AccountExpiredException("Expired"));
            } else {
                handlerExceptionResolver.resolveException(request,response,null, e);
            }
        }
    }
}
