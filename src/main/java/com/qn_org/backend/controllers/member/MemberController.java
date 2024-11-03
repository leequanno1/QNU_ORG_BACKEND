package com.qn_org.backend.controllers.member;

import com.qn_org.backend.models.Member;
import com.qn_org.backend.responses.QnuResponseEntity;
import com.qn_org.backend.services.exceptions.NoAuthorityToDoActionException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@CrossOrigin
public class MemberController {
    private final MemberService service;

    @PutMapping("/add")
    public QnuResponseEntity<MemberDTO> add(@RequestBody AddMemberToOrgRequest request) throws NoAuthorityToDoActionException {
        return new QnuResponseEntity<>(service.add(request), HttpStatus.OK);
    }

    @PutMapping("/remove")
    public QnuResponseEntity<MemberDTO> remove(@RequestBody MemberIdRequest request) {
        return new QnuResponseEntity<>(service.remove(request), HttpStatus.OK);
    }

//    @PutMapping("set-role")
    @ExceptionHandler(NoAuthorityToDoActionException.class)
    public QnuResponseEntity<String> NoAuthorityToDoActionExceptionHandler() {
        return new QnuResponseEntity<>("User dont have authority to do this action", HttpStatus.BAD_REQUEST);
    }
}
