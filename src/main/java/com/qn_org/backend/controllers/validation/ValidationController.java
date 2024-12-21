package com.qn_org.backend.controllers.validation;

import com.qn_org.backend.controllers.user.UserIdRequest;
import com.qn_org.backend.controllers.user.UserService;
import com.qn_org.backend.responses.QnuResponseEntity;
import com.qn_org.backend.services.exceptions.*;
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

    @PostMapping("/send-active-code")
    public QnuResponseEntity<String> handleSendEmail(HttpServletRequest servletRequest) throws MessagingException, EmailNotValidException, NoAuthorityToDoActionException {
        return new QnuResponseEntity<>(emailService.handleSendActiveAccountCode(servletRequest), HttpStatus.OK);
    }

    @PostMapping("/send-forget-password-code")
    public QnuResponseEntity<String> handleSendForgetPasswordCode(@RequestBody UserIdRequest request) throws IdNotExistException, MessagingException, NoAuthorityToDoActionException, EmailNotValidException {
        return new QnuResponseEntity<>(emailService.handleSendForgetPasswordCode(request), HttpStatus.OK);
    }

    @PostMapping("/change_password")
    public QnuResponseEntity<String> handleChangePassword(@RequestBody ChangePasswordRequest request) throws NoAuthorityToDoActionException, ValidationCodeExpiredException, InvalidValidationCodeException {
        return new QnuResponseEntity<>(userService.handleChangePassword(request),HttpStatus.OK);
    }

    @PostMapping("/change_forgot_password")
    public QnuResponseEntity<String> handleChangeForgotPassword(@RequestBody ChangePasswordRequest request) throws NoAuthorityToDoActionException, ValidationCodeExpiredException, InvalidValidationCodeException {
        return new QnuResponseEntity<>(userService.handleChangePassword(request),HttpStatus.OK);
    }

    @PostMapping("/active_account")
    public QnuResponseEntity<String> handleActiveAccount(@RequestBody ActiveAccountRequest request, HttpServletRequest servletRequest) throws NoAuthorityToDoActionException {
        return new QnuResponseEntity<>(userService.handleActiveAccount(request,servletRequest),HttpStatus.OK);
    }

    @ExceptionHandler(MessagingException.class)
    public QnuResponseEntity<String> handleMessagingException(){
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
        return new QnuResponseEntity<>("You have no authority to do this action.", HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(IdNotExistException.class)
    public QnuResponseEntity<String> handleIdNotExistException(){
        return new QnuResponseEntity<>("Id is not exist.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidValidationCodeException.class)
    public QnuResponseEntity<String> handleInvalidValidationCodeException(){
        return new QnuResponseEntity<>("Validation code is invalid", HttpStatus.BAD_REQUEST);
    }
}
