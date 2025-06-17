package com.qn_org.backend.services;

import com.qn_org.backend.config.JwtService;
import com.qn_org.backend.controllers.user.UserIdRequest;
import com.qn_org.backend.controllers.validation.SendEmailRequest;
import com.qn_org.backend.models.ValidationCode;
import com.qn_org.backend.repositories.UserRepository;
import com.qn_org.backend.repositories.ValidationCodeRepository;
import com.qn_org.backend.services.exceptions.EmailNotValidException;
import com.qn_org.backend.services.exceptions.IdNotExistException;
import com.qn_org.backend.services.exceptions.NoAuthorityToDoActionException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final JwtService jwtService;
    private final ValidationCodeRepository validationCodeRepository;
    private final UserRepository userRepository;

    @Value("${spring.mail.username}")
    private String sourceEmail;

    private String handelSendMail(String mailTo, String userId) throws EmailNotValidException, MessagingException {
        var validationCodeStr = String.format("%08d", new Random().nextInt(100000000));
        var user = userRepository.getReferenceById(userId);
        if(!user.getEmailAddress().equals(mailTo)){
            throw new EmailNotValidException();
        }
        var validationCode = validationCodeRepository.findByValidationEmail(mailTo);
        if(validationCode == null) {
            validationCode = ValidationCode.builder().build();
        }
        validationCode.setValidationEmail(mailTo);
        validationCode.setValidationCode(validationCodeStr);
        validationCode.setInsDate(new Date());
        validationCode.setDelFlg(false);
        validationCode.setUserType(getUserType(user.getUserInfoKey()));
        validationCodeRepository.save(validationCode);
        String subject = "Mã xác thực email";
        String htmlBody = EmailService.validatedCodeEmailBody(validationCodeStr);
        sendHtmlEmail(mailTo, subject, htmlBody);
        return "Mail sent successful";
    }

    public static String validatedCodeEmailBody(String validationCode) {
        String mailBody = "<p>Mã xác nhận email của bạn là <b>%s</b></p>";
        return String.format(mailBody, validationCode);
    }

    public void sendHtmlEmail(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        helper.setFrom(sourceEmail);

        mailSender.send(mimeMessage);
        System.out.println("HTML Email sent successfully!");
    }

    public String handleSendValidatedEmail(SendEmailRequest request, HttpServletRequest servletRequest) throws MessagingException, EmailNotValidException {
        String mailTo = request.getEmail();
        String userId = jwtService.extractUserId(servletRequest);
        return handelSendMail(mailTo, userId);
    }



    public String handleSendActiveAccountCode(HttpServletRequest servletRequest) throws MessagingException, EmailNotValidException, NoAuthorityToDoActionException {
        var userId = jwtService.extractUserId(servletRequest);
        var user = userRepository.getReferenceById(userId);
        if(!user.getUserId().isBlank() && user.getEmailAddress() != null && !user.getEmailAddress().isBlank()) {
            return handleSendValidatedEmail(SendEmailRequest.builder().email(user.getEmailAddress()).build(), servletRequest);
        }
        throw new NoAuthorityToDoActionException();
    }

    private int getUserType(String userInfoKey) {
        String regex = userInfoKey.substring(0,3);
        if(regex.equals("TEA")) {
            return 1;
        }
        if(regex.equals("STA")) {
            return 2;
        }
        if(regex.equals("STU")) {
            return 3;
        }
        return 0;
    }

    public String handleSendForgetPasswordCode(UserIdRequest request) throws IdNotExistException, NoAuthorityToDoActionException, MessagingException, EmailNotValidException {
        var userId = request.getUserId();
        if(userId == null || userId.isBlank()) {
            throw new IdNotExistException();
        }
        try {
            var user = userRepository.getReferenceById(userId);
            var email = user.getEmailAddress();
            return handelSendMail(email,userId);
        } catch (EntityNotFoundException e) {
            throw new NoAuthorityToDoActionException();
        }
    }
}
