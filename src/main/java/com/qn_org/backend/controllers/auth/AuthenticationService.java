package com.qn_org.backend.controllers.auth;

import com.qn_org.backend.config.JwtService;
import com.qn_org.backend.controllers.staff.StaffInfoService;
import com.qn_org.backend.controllers.student.StudentInfoService;
import com.qn_org.backend.models.StaffInfo;
import com.qn_org.backend.models.StudentInfo;
import com.qn_org.backend.models.User;
import com.qn_org.backend.models.enums.UserType;
import com.qn_org.backend.repositories.UserRepository;
import com.qn_org.backend.services.QnuService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements QnuService<User> {

    private final UserRepository repository;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final StudentInfoService studentInfoService;
    private final StaffInfoService staffInfoService;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .userId(request.getUserId())
                .password(passwordEncoder.encode(request.getPassword()))
                .emailAddress(request.email)
                .userInfoKey(request.handleGetUserInfoKey())
                .displayName(request.displayName)
                .userType(request.userType)
                .insDate(new Date())
                .passwordValidatedFlg(false)
                .build();
        repository.save(user);
        handleSaveRepository(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserId(),
                        request.getPassword()
                )
        );
        var user = repository.findByUserId(request.getUserId())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public boolean handleSaveRepository(User entity) {
        var res = entity.getUserInfoKey() == null;
        if(!res) {
            UserType typeEnum = UserType.fromValue(entity.getUserType());
            switch (typeEnum){
                case STAFF -> staffInfoService.save(StaffInfo.builder()
                        .staffKey(entity.getUserInfoKey())
                        .isTeacher(false)
                        .build());
                case TEACHER -> staffInfoService.save(StaffInfo.builder()
                        .staffKey(entity.getUserInfoKey())
                        .isTeacher(true)
                        .build());
                case STUDENT -> studentInfoService.save(StudentInfo.builder()
                        .studentKey(entity.getUserInfoKey())
                        .build());
            }
        }
        return res;
    }

    public ValidateResponse validation(String bearerToken) {
        if(!bearerToken.startsWith("Bearer")){
            throw new ExpiredJwtException(null, null,null);
        }
        var jwt = bearerToken.substring(7);
        var userId = jwtService.extractUsername(jwt);
        System.out.println(userId);
        return ValidateResponse
                .builder()
                .isValidated(jwtService.isTokenValid(jwt,userDetailsService.loadUserByUsername(userId)))
                .problemCause("")
                .build();
    }
}
