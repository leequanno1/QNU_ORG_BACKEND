package com.qn_org.backend.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.qn_org.backend.config.JwtService;
import com.qn_org.backend.controllers.member.ManageMember;
import com.qn_org.backend.controllers.member.MemberIdRequest;
import com.qn_org.backend.repositories.MemberRepository;
import com.qn_org.backend.responses.QnuResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class MemberAspect {
    private final MemberRepository memberRepository;

    private final JwtService jwtService;


    // Key: OrgId, Value: response
    private final Map<String, QnuResponseEntity<List<ManageMember>>> manageMemberResponseCache = new HashMap<>();

    // Key: OrgId, Value: is data has been modified flg (change = true)
    private final Map<String, Boolean> modifyDataFlgCache = new HashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String ROLE_MODIFY_LOGGING_FILE_PATH = "src/public/role_modify_logging.csv";

    @Pointcut("execution(* com.qn_org.backend.controllers.member.MemberController.add(..)) && args(..,orgId)")
    public void afterAdd(String orgId) {}

    @Pointcut("execution(* com.qn_org.backend.controllers.member.MemberController.remove(..)) && args(..,request)")
    public void afterRemove(MemberIdRequest request) {}

    @Pointcut(value = "execution(* com.qn_org.backend.controllers.member.MemberController.changeMemberRole(..)) && args(request, servletRequest)", argNames = "request,servletRequest")
    public void afterChangeRole(ManageMember request, HttpServletRequest servletRequest) {}


    @Around("execution(* com.qn_org.backend.controllers.member.MemberController.getManageMembers(..))")
    public Object returnCache(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String orgId = "";

        for (Object arg : args) {
            if (arg instanceof String) {
                orgId = (String) arg;
            }
        }
        QnuResponseEntity<List<ManageMember>> result;
        try {
            if (manageMemberResponseCache.containsKey(orgId)
                    && modifyDataFlgCache.containsKey(orgId)
                    && !modifyDataFlgCache.get(orgId)
            ) {
                System.out.println(orgId+  ": load from cache");
                return manageMemberResponseCache.get(orgId);
            } else {
                result = (QnuResponseEntity<List<ManageMember>>) joinPoint.proceed();
                manageMemberResponseCache.put(orgId, result);
                modifyDataFlgCache.put(orgId, false);
                return result;
            }
        } catch (Throwable ex) {
            throw ex;
        }
    }

    @After(value = "afterAdd(orgId)", argNames = "orgId")
    public void handleAfterAdd(String orgId) {
        modifyDataFlgCache.put(orgId,true);
    }
    
    @After(value = "afterRemove(request)", argNames = "request")
    public void handleAfterRemove(MemberIdRequest request) {
        var member = memberRepository.getReferenceById(request.getMemberId());
        try {
            var orgId = member.getOrganization().getOrgId();
            modifyDataFlgCache.put(orgId,true);
        } catch (Exception ignored) { }
    }

    @Around(value = "afterChangeRole(request, servletRequest)", argNames = "pjp,request,servletRequest")
    public Object handleAfterChangeRole(ProceedingJoinPoint pjp, ManageMember request, HttpServletRequest servletRequest) {
        Date startTime = new Date();
        String orgId = request.getOrgId();
        String userId = jwtService.extractUserId(servletRequest);
        String toRole = request.getRoleId();
        String modifyUserId = request.getUserId();
        Object result;
        try {
            result = pjp.proceed();
            modifyDataFlgCache.put(orgId, true);
            ObjectNode dynamicObject = objectMapper.createObjectNode();
            dynamicObject.put("orgId" , orgId);
            dynamicObject.put("editor" , userId);
            dynamicObject.put("modifyUserId" , modifyUserId );
            dynamicObject.put("toRole" , toRole);
            dynamicObject.put("atTime" , startTime.toString());
            String jsonString = dynamicObject.toString();
            CsvHelper.writeJsonToCsv(jsonString, ROLE_MODIFY_LOGGING_FILE_PATH);
            return result;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
