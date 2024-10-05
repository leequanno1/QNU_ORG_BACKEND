package com.qn_org.backend.config;

import com.qn_org.backend.responses.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleInternalServerError(Exception ex) {
        return new ResponseEntity<>(new ApiResponse<>(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ApiResponse<String>> handleForbiddenException(SecurityException ex) {
        return new ResponseEntity<>(new ApiResponse<>(403, "Forbidden"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse<String>> handleExpiredJwtException(ExpiredJwtException ex) {
        return new ResponseEntity<>(new ApiResponse<>(403, "Jwt token is expired"), HttpStatus.FORBIDDEN);
    }
}
