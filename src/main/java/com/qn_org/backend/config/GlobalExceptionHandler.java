package com.qn_org.backend.config;

import com.qn_org.backend.responses.QnuResponseEntity;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountExpiredException.class)
    public QnuResponseEntity<Object> handleInternalServerError(Exception ex) {
        return new QnuResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(io.jsonwebtoken.security.SignatureException.class)
    public QnuResponseEntity<Object> handleSignatureException(Exception ex){
        return new QnuResponseEntity<>("Token is invalid", HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public QnuResponseEntity<Object> handleForbiddenException(SecurityException ex) {
        return new QnuResponseEntity<>("Token expired", HttpStatus.FORBIDDEN);

    }
}
