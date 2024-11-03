package com.qn_org.backend.controllers.member;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qn_org.backend.models.Member;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberDTO {
    private String memberId;

    private String orgId;

    private String userId;

    private String roleId;

    private int roleLevel;

    private Date insDate;

    private boolean delFlg;

    public MemberDTO(Member member) {
        this.memberId = member.getMemberId();
        this.orgId = member.getOrganization().getOrgId();
        this.userId = member.getUserId();
        this.roleId = member.getRoleId();
        this.roleLevel = member.getRoleLevel();
        this.insDate = member.getInsDate();
        this.delFlg = member.isDelFlg();
    }

    public static List<MemberDTO> fromList(List<Member> members) {
        List<MemberDTO> dto = new ArrayList<>();
        for(var member : members) {
            dto.add(new MemberDTO(member));
        }
        return dto;
    }
}
