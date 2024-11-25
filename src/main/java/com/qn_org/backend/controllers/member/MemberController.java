package com.qn_org.backend.controllers.member;

import com.qn_org.backend.models.Member;
import com.qn_org.backend.responses.QnuResponseEntity;
import com.qn_org.backend.services.exceptions.NoAuthorityToDoActionException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@CrossOrigin
public class MemberController {
    private final MemberService service;

    @PostMapping("/add")
    public QnuResponseEntity<List<MemberDTO>> add(@RequestBody AddMemberToOrgRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        return new QnuResponseEntity<>(service.add(request, servletRequest), HttpStatus.OK);
    }

    @PostMapping("/remove")
    public QnuResponseEntity<MemberDTO> remove(@RequestBody MemberIdRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        return new QnuResponseEntity<>(service.remove(request, servletRequest), HttpStatus.OK);
    }

    @PostMapping("/get_member_info")
    public QnuResponseEntity<List<MemberInfo>> getMemberInfo(@RequestBody MemberInfoRequest request) {
        return new QnuResponseEntity<>(service.getMemberInfo(request), HttpStatus.OK);
    }

    @PostMapping("/get_info_by_user_id_and_org_id")
    public QnuResponseEntity<MemberInfo> getMemberInfo(@RequestBody UserAndOrgIdRequest request) {
        return new QnuResponseEntity<>(service.getMemberInfo(request), HttpStatus.OK);
    }

    @GetMapping("/get-manage-member")
    public QnuResponseEntity<List<ManageMember>> getManageMembers(@RequestParam String orgId) {
        return new QnuResponseEntity<>(service.getManagedMembers(orgId), HttpStatus.OK);
    }

    @PostMapping("/get_preview_member")
    public QnuResponseEntity<List<PreviewMember>> getPreviewMember(@RequestBody GetReviewMemberRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        return new QnuResponseEntity<>(service.getPreviewMember(request, servletRequest), HttpStatus.OK);
    }

    @PostMapping("/change_member_role")
    public QnuResponseEntity<ManageMember> changeMemberRole(@RequestBody ManageMember request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        return new QnuResponseEntity<>(service.changeMemberRole(request, servletRequest), HttpStatus.OK);
    }

//    @PutMapping("set-role")

    @ExceptionHandler(NoAuthorityToDoActionException.class)
    public QnuResponseEntity<String> NoAuthorityToDoActionExceptionHandler() {
        return new QnuResponseEntity<>("User dont have authority to do this action", HttpStatus.BAD_REQUEST);
    }
}
