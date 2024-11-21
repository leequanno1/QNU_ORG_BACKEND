package com.qn_org.backend.controllers.validation;

import com.qn_org.backend.controllers.user.UserService;
import com.qn_org.backend.responses.QnuResponseEntity;
import com.qn_org.backend.services.exceptions.EmailNotValidException;
import com.qn_org.backend.services.exceptions.NoAuthorityToDoActionException;
import com.qn_org.backend.services.exceptions.ValidationCodeExpiredException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/validation")
@RequiredArgsConstructor
public class ValidationController {
    private final EmailService emailService;
    private final UserService userService;

    @PostMapping("/send_email")
    public QnuResponseEntity<String> handleSendEmail(@RequestBody SendEmailRequest request, HttpServletRequest servletRequest) throws MessagingException, EmailNotValidException {
        return new QnuResponseEntity<>(emailService.handleSendValidatedEmail(request, servletRequest), HttpStatus.OK);
    }

    @PostMapping("/change_password")
    public QnuResponseEntity<String> handleChangePassword(@RequestBody ChangePasswordRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException, ValidationCodeExpiredException {
        return new QnuResponseEntity<>(userService.handleChangePassword(request,servletRequest),HttpStatus.OK);
    }


    @ExceptionHandler(MessagingException.class)
    public QnuResponseEntity<String> handleMessagingException(MessagingException exception){
        exception.printStackTrace();
        return new QnuResponseEntity<>("Some errors happened while sending email", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmailNotValidException.class)
    public QnuResponseEntity<String> handleEmailNotValidException(){
        return new QnuResponseEntity<>("Your email is invalid", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationCodeExpiredException.class)
    public QnuResponseEntity<String> handleValidationCodeExpiredException(){
        return new QnuResponseEntity<>("Code expired", HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(NoAuthorityToDoActionException.class)
    public QnuResponseEntity<String> handleNoAuthorityToDoActionException(){
        return new QnuResponseEntity<>("Change password fails", HttpStatus.EXPECTATION_FAILED);
    }
}
