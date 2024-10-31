package com.qn_org.backend.controllers.member;

import com.qn_org.backend.models.Member;
import com.qn_org.backend.responses.QnuResponseEntity;
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
    public QnuResponseEntity<Member> add(@RequestBody AddMemberToOrgRequest request) {
        return new QnuResponseEntity<>(service.add(request), HttpStatus.OK);
    }

    @PutMapping("/remove")
    public QnuResponseEntity<Member> remove(@RequestBody MemberIdRequest request) {
        return new QnuResponseEntity<>(service.remove(request), HttpStatus.OK);
    }
}
