package com.qn_org.backend.controllers.user;

import com.qn_org.backend.responses.QnuResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;

    @PostMapping("/get_user_info")
    public QnuResponseEntity<List<UserInfoResponse>> getUserInfos(@RequestBody UserInfoRequest request) {
        return new QnuResponseEntity<>(userService.getUserInfos(request), HttpStatus.OK);
    }

    @PostMapping("/get_user_info_from_member_id")
    public QnuResponseEntity<List<UserInfoResponse>> getUserInfosFromMemberId(@RequestBody UserInfosFromMemberIdRequest request) {
        return new QnuResponseEntity<>(userService.getUserInfosFromMemberId(request), HttpStatus.OK);
    }

    @PostMapping("/get_stu_user_info")
    public QnuResponseEntity<StudentUserInfoResponse> getStudentUserInfo(@RequestBody UserIdRequest request) {
        return new QnuResponseEntity<>(userService.getStudentUserInfo(request), HttpStatus.OK);
    }

    @PostMapping(value = "/update_user-info", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public QnuResponseEntity<UserInfoResponse> updateUserInfo(@ModelAttribute UpdateUserInfoRequest request, HttpServletRequest servletRequest) throws IOException {
        return new QnuResponseEntity<>(userService.updateUserInfo(request, servletRequest), HttpStatus.OK);
    }

    @ExceptionHandler(IOException.class)
    public QnuResponseEntity<String> handleIOException() {
        return new QnuResponseEntity<>("Some shit happened while saving image(s).", HttpStatus.OK);
    }

}
