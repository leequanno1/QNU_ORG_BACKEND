package com.qn_org.backend.controllers.user;

import com.qn_org.backend.config.JwtService;
import com.qn_org.backend.controllers.image.ImageService;
import com.qn_org.backend.controllers.validation.ChangePasswordRequest;
import com.qn_org.backend.models.ValidationCode;
import com.qn_org.backend.repositories.MemberRepository;
import com.qn_org.backend.repositories.UserRepository;
import com.qn_org.backend.repositories.ValidationCodeRepository;
import com.qn_org.backend.services.exceptions.NoAuthorityToDoActionException;
import com.qn_org.backend.services.exceptions.ValidationCodeExpiredException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final ImageService imageService;
    private final ValidationCodeRepository validationCodeRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserInfoResponse> getUserInfos(UserInfoRequest request) {
        if (request.getUserIds().isEmpty()) {
            return new ArrayList<>();
        }
        return repository.getUserInfos(request.getUserIds());
    }

    public List<UserInfoResponse> getUserInfosFromMemberId(UserInfosFromMemberIdRequest request) {
        if (request.getMemberId().isEmpty()) return new ArrayList<>();
        List<String> userIdList = memberRepository.getUserIdByMemberId(request.getMemberId());
        return getUserInfos(new UserInfoRequest(userIdList));
    }

    public StudentUserInfoResponse getStudentUserInfo(UserIdRequest request) {
        return repository.getStudentUserInfo(request.getUserId());
    }

    public UserInfoResponse updateUserInfo(UpdateUserInfoRequest request, HttpServletRequest servletRequest) throws IOException {
        String userId = jwtService.extractUserId(servletRequest);
        if (userId != null) {
            var imageAPI = "/api/image/";
            var userInfo = repository.getReferenceById(userId);
            if (request.getDisplayName() != null && !request.getDisplayName().isBlank())
                userInfo.setDisplayName(request.getDisplayName());
            if (request.getUserAvatar() != null)
                userInfo.setUserAvatar(imageAPI + imageService.handleSaveImage(request.getUserAvatar(), ImageService.generateUniqueImageName()));
            if (request.getUserBackground() != null)
                userInfo.setUserBackground(imageAPI + imageService.handleSaveImage(request.getUserBackground(), ImageService.generateUniqueImageName()));
            repository.save(userInfo);
            return new UserInfoResponse(userInfo);
        }
        return null;
    }

    public String handleChangePassword(ChangePasswordRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException, ValidationCodeExpiredException {
        if (!request.getNewPassword().equals(request.getRepeatPassword())) {
            throw new NoAuthorityToDoActionException();
        }
        var userId = jwtService.extractUserId(servletRequest);
        if (userId != null) {
            var user = repository.getReferenceById(userId);
            if (user.getEmailAddress().equals(request.getEmailAddress())) {
                var validationCode = validationCodeRepository.findByValidationEmail(user.getEmailAddress());
                if (validationCode != null && validationCode.getValidationCode().equals(request.getValidationCode())) {
                    if(isValidationCodeExpired(validationCode)) {
                        throw new ValidationCodeExpiredException();
                    }
                    validationCodeRepository.delete(validationCode);
                    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                    repository.save(user);
                    return "Change password successfully";
                }
            }
        }
        throw new NoAuthorityToDoActionException();
    }

    private boolean isValidationCodeExpired(ValidationCode validationCode) {
        var time = new Date().getTime() - validationCode.getInsDate().getTime();
        return time > 300000;
    }
}
