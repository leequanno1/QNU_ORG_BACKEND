package com.qn_org.backend.controllers.auth;

import com.qn_org.backend.responses.QnuResponseEntity;
import com.qn_org.backend.services.CookieConverter;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


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
    public QnuResponseEntity<Boolean> validate(@RequestBody TokenRequest request) {
        return new QnuResponseEntity<>(service.validation(request.getBearerToken()), HttpStatus.OK);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public QnuResponseEntity<Boolean> validate() {
        return new QnuResponseEntity<>(false, HttpStatus.FORBIDDEN);
    }
}
