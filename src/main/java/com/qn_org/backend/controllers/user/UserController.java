package com.qn_org.backend.controllers.user;

import com.qn_org.backend.responses.QnuResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
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
}
