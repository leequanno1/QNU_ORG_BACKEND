package com.qn_org.backend.aspect;

import com.qn_org.backend.controllers.member.PreviewMember;
import com.qn_org.backend.responses.QnuResponseEntity;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class UserAspect {
    private QnuResponseEntity<List<PreviewMember>> response;
    private boolean isEdited = true;

    @Pointcut("execution(* com.qn_org.backend.controllers.user.UserController.getUserForAdmin(..))")
    public void aroundGetUser(){}

    @Pointcut("execution(* com.qn_org.backend.controllers.user.UserController.updateUserInfo(..))")
    public void afterUpdate(){}

    @Pointcut("execution(* com.qn_org.backend.controllers.user.UserController.deleteUser(..))")
    public void afterDelete(){}

    @Pointcut("execution(* com.qn_org.backend.controllers.auth.AuthenticationController.multipleRegister(..))")
    public void afterRegister(){}

    @Around( value = "aroundGetUser()")
    public Object handleAroundGetUser(ProceedingJoinPoint pjp) {
        if(isEdited) {
            try {
                response = (QnuResponseEntity<List<PreviewMember>>) pjp.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
            isEdited = false;
        } else {
            System.out.println("Load from cache");
            }
        return response;
    }

    @After(value = "afterUpdate()")
    public void handleAfterUpdate(){
        isEdited = true;
    }

    @After(value = "afterDelete()")
    public void handleAfterDelete(){
        isEdited = true;
    }

    @After(value = "afterRegister()")
    public void handleAfterRegister(){
        isEdited = true;
    }
}
