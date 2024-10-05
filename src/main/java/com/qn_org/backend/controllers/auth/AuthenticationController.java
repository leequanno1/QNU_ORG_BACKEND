package com.qn_org.backend.controllers.auth;

import com.qn_org.backend.responses.ApiResponse;
import com.qn_org.backend.responses.QnuResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
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
}
