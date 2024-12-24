package com.qn_org.backend.aspect;

import com.qn_org.backend.repositories.MemberRepository;
import com.qn_org.backend.repositories.UserRepository;
import com.qn_org.backend.services.EmailService;

public class EmailHelper implements Runnable {
    private EmailService emailService;
    private String mailTo;
    private String joinerName;
    private String orgName;

    public EmailHelper(
            EmailService emailService,
            String mailTo,
            String joinerName,
            String orgName)
    {
        this.emailService = emailService;
        this.mailTo = mailTo;
        this.joinerName = joinerName;
        this.orgName = orgName;
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        try {
            var mailContent = String.format(
                    "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"><style>body{font-family:Arial,sans-serif;margin:0;padding:0;background-color:#f4f4f4}.email-container{max-width:600px;margin:20px auto;background:#fff;border-radius:8px;overflow:hidden;box-shadow:0 2px 4px rgba(0,0,0,.1)}.header{background-color:#007bff;color:#fff;padding:20px;text-align:center;font-size:18px}.content{padding:20px;color:#333;font-size:16px;line-height:1.5}.footer{background-color:#f4f4f4;padding:10px;text-align:center;font-size:12px;color:#777}</style></head><body><div class=\"email-container\"><div class=\"header\">Thông Báo Tham Gia Sự Kiện</div><div class=\"content\"><p>Thành viên <strong>%s</strong> đã tham gia sự kiện của bạn trong nhóm <strong>%s</strong>.</p><p>Cảm ơn bạn đã sử dụng hệ thống của chúng tôi!</p></div><div class=\"footer\">&copy; QNU CLB Management. All rights reserved.</div></div></body></html>",
                    joinerName,
                    orgName);
            var subject = "Thông báo tham gia sự kiện";
            emailService.sendHtmlEmail(mailTo,subject, mailContent);
            System.out.println("Send email successful");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Send email not successful");
        }
    }
}
