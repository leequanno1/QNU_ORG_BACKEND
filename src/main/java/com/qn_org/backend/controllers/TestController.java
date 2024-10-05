package com.qn_org.backend.controllers;

import com.qn_org.backend.responses.QnuResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {
    @GetMapping("/test")
    public QnuResponseEntity<String> getTestAPI() {
        return new QnuResponseEntity<>("Hello and secured", HttpStatus.OK);
    }
}
