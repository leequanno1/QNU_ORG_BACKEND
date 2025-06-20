package com.qn_org.backend.controllers.user;

import com.qn_org.backend.controllers.member.PreviewMember;
import com.qn_org.backend.responses.QnuResponseEntity;
import com.qn_org.backend.services.exceptions.IdNotExistException;
import com.qn_org.backend.services.exceptions.NoAuthorityToDoActionException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @PostMapping("/get_sta_user_info")
    public QnuResponseEntity<StaffUserInfoResponse> getStaffUserInfo(@RequestBody UserIdRequest request) {
        return new QnuResponseEntity<>(userService.getStaffUserInfo(request), HttpStatus.OK);
    }

    @PostMapping(value = "/update_user-info", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public QnuResponseEntity<UserInfoResponse> updateUserInfo(@ModelAttribute UpdateUserInfoRequest request, HttpServletRequest servletRequest) throws IOException {
        return new QnuResponseEntity<>(userService.updateUserInfo(request, servletRequest), HttpStatus.OK);
    }

    @GetMapping("/get_user_for_admin")
    public QnuResponseEntity<List<PreviewMember>> getUserForAdmin(HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        return new QnuResponseEntity<>(userService.getUserForAdmin(servletRequest), HttpStatus.OK);
    }

    @GetMapping("/get-extend-user-info")
    public QnuResponseEntity<ExpandUserInfo> getExpandUserInfo(@RequestParam String userId, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        return new QnuResponseEntity<>(userService.getExpandUserInfo(userId,servletRequest), HttpStatus.OK);
    }

    @DeleteMapping("/delete-user")
    public QnuResponseEntity<String> deleteUser(@RequestBody UserIdRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException, IdNotExistException {
        return new QnuResponseEntity<>(userService.deleteUser(request, servletRequest), HttpStatus.OK);
    }

    @ExceptionHandler(IOException.class)
    public QnuResponseEntity<String> handleIOException() {
        return new QnuResponseEntity<>("Some error happened while saving image(s).", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoAuthorityToDoActionException.class)
    public QnuResponseEntity<String> handleNoAuthorityToDoActionException() {
        return new QnuResponseEntity<>("User dont have authority to do this action", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IdNotExistException.class)
    public QnuResponseEntity<String> handleIdNotExistException() {
        return new QnuResponseEntity<>("Id is not exist", HttpStatus.BAD_REQUEST);
    }
}
