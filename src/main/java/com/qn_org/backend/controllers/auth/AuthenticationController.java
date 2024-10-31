package com.qn_org.backend.controllers.auth;

import com.qn_org.backend.responses.QnuResponseEntity;
import com.qn_org.backend.services.exceptions.EditorNoAuthorityException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.SignatureException;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService service;
    @PostMapping("/register")
    public QnuResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return new QnuResponseEntity<>(service.register(request), HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public QnuResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return new QnuResponseEntity<>(service.authenticate(request), HttpStatus.OK);
    }

    @PostMapping("/validate")
    public QnuResponseEntity<ValidateResponse> validate(@RequestBody ValidateRequest request) {
        return new QnuResponseEntity<>(service.validation(request.getBearerToken()), HttpStatus.OK);
    }

    @PutMapping("/multiple_register")
    public QnuResponseEntity<String> multipleRegister(@RequestBody MultipleRegisterRequest request) throws EditorNoAuthorityException {
        return new QnuResponseEntity<>(service.multipleRegister(request), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public QnuResponseEntity<ValidateResponse> exHandler (Exception ex) {
        ex.printStackTrace();
        ValidateResponse response = ValidateResponse
                                    .builder()
                                    .isValidated(false)
                                    .problemCause(ex.getMessage())
                                    .build();
        return new QnuResponseEntity<>(response,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(io.jsonwebtoken.security.SignatureException.class)
    public QnuResponseEntity<ValidateResponse> SignatureExceptionHandler(SignatureException ex) {
        ValidateResponse response = ValidateResponse
                .builder()
                .isValidated(false)
                .problemCause(ex.getMessage())
                .build();
        return new QnuResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public QnuResponseEntity<ValidateResponse> ExpiredJwtExceptionHandler(ExpiredJwtException ex) {
        ValidateResponse response = ValidateResponse
                .builder()
                .isValidated(false)
                .problemCause(ex.getMessage())
                .build();
        return new QnuResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public QnuResponseEntity<ValidateResponse> IllegalArgumentExceptionHandler(IllegalArgumentException e) {
        e.printStackTrace();
        ValidateResponse response = ValidateResponse
                .builder()
                .isValidated(false)
                .problemCause(e.getMessage())
                .build();
        return new QnuResponseEntity<>(response,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public QnuResponseEntity<ValidateResponse> MalformedJwtExceptionHandler(MalformedJwtException e) {
        e.printStackTrace();
        ValidateResponse response = ValidateResponse
                .builder()
                .isValidated(false)
                .problemCause(e.getMessage())
                .build();
        return new QnuResponseEntity<>(response,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public QnuResponseEntity<ValidateResponse> UnsupportedJwtExceptionHandler(UnsupportedJwtException e) {
        e.printStackTrace();
        ValidateResponse response = ValidateResponse
                .builder()
                .isValidated(false)
                .problemCause(e.getMessage())
                .build();
        return new QnuResponseEntity<>(response,HttpStatus.FORBIDDEN);
    }
}
