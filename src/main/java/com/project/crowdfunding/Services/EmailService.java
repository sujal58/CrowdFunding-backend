package com.project.crowdfunding.Services;

import com.project.crowdfunding.Exception.ApiException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

   private final JavaMailSender mailSender;

   @Value("${spring.mail.username}")
   private String SENDER_EMAIL;

    private static final String APP_NAME = "fundSaathi";

    @Autowired
    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }


    public void sendOTPEmail(String recipientEmail, String otp) throws AddressException, MessagingException {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mailHelper = new MimeMessageHelper(message, true, "UTF-8");

            mailHelper.setFrom(SENDER_EMAIL);
            mailHelper.setTo(recipientEmail);
            mailHelper.setSubject("Your OTP for " + APP_NAME + " verification");

            // HTML content for the email
            String htmlContent = """
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <style>
                            body { font-family: Arial, sans-serif; color: #333; }
                            .container { max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px; }
                            .header { background-color: #f8f8f8; padding: 10px; text-align: center; }
                            .otp { font-size: 24px; font-weight: bold; color: #007bff; text-align: center; margin: 20px 0; }
                            .info { font-size: 14px; color: #555; text-align: center; }
                            .footer { font-size: 12px; color: #999; text-align: center; margin-top: 20px; }
                            .button { display: inline-block; padding: 10px 20px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 5px; }
                        </style>
                    </head>
                    <body>
                        <div class="container">
                            <div class="header">
                                <h2>Welcome to %s</h2>
                            </div>
                            <p style="text-align: center;">Please use the following One-Time Password (OTP) to complete your verification:</p>
                            <div class="otp">%s</div>
                            <p class="info">This OTP is valid for 5 minutes. Do not share it with anyone.</p>
                            <div class="footer">
                                <p>If you didn’t request this OTP, please ignore this email.</p>
                                <p>&copy; %s %d. All rights reserved.</p>
                            </div>
                        </div>
                    </body>
                    </html>
                    """.formatted(APP_NAME, otp, APP_NAME, java.time.Year.now().getValue());


            mailHelper.setText(htmlContent, true);
            mailSender.send(message);
            log.info("OTP email sent to {}", recipientEmail);
        }catch (Exception e){
            log.error("Error while sending mail:",e);
            throw new ApiException("Error while sending mail");
        }
    }


    public void sendKycStatus(String recipientEmail, String kycStatus) throws AddressException, MessagingException {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mailHelper = new MimeMessageHelper(message, true, "UTF-8");

            mailHelper.setFrom(SENDER_EMAIL);
            mailHelper.setTo(recipientEmail);
            mailHelper.setSubject("Your Kyc update for " + APP_NAME );

            // HTML content for the email
            String htmlContent = """
    <!DOCTYPE html>
    <html>
    <head>
        <style>
            body { font-family: Arial, sans-serif; color: #333; }
            .container { max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px; }
            .header { background-color: #f8f8f8; padding: 10px; text-align: center; }
            .otp { font-size: 24px; font-weight: bold; color: #007bff; text-align: center; margin: 20px 0; }
            .info { font-size: 14px; color: #555; text-align: center; }
            .note { font-size: 14px; color: #d9534f; text-align: left; margin-top: 15px; }
            .footer { font-size: 12px; color: #999; text-align: center; margin-top: 20px; }
            .button { display: inline-block; padding: 10px 20px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 5px; }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <h2>%s - KYC Status Update</h2>
            </div>
            <p style="text-align: center;">Your KYC verification status has been updated to:</p>
            <div class="otp">%s</div>
            %s
            <p class="info">If you have any questions or believe this is a mistake, please contact our support team.</p>
            <div class="footer">
                <p>Thank you for using %s.</p>
                <p>&copy; %s %d. All rights reserved.</p>
            </div>
        </div>
    </body>
    </html>
    """.formatted(
                    APP_NAME,
                    kycStatus,
                    kycStatus.equals("rejected")
                            ? """
                <div class='note'>
                    <p>Your KYC was not approved due to one or more of the following reasons:</p>
                    <div class='note'>
                        <p>Your KYC was not approved due to one or more of the following reasons:</p>
                           <ul>
                            <li>Blurry or unreadable document image</li>
                            <li>Mismatched between document number and given number</li>
                            <li>Document photo and submitted person is unverified</li>
                            <li>Mismatch between name and document</li>
                            <li>Incomplete or missing information</li>
                           </ul>
                        <p>Please re-submit your KYC with corrected information.</p>
                    </div>
                </div>
              """
                            : kycStatus.equals("verified")
                            ? "<p class='info'>Congratulations! Your account is now verified. You can now create and manage campaigns.</p>"
                            : ""
                    ,
                    APP_NAME,
                    APP_NAME,
                    java.time.Year.now().getValue()
            );




            mailHelper.setText(htmlContent, true);
            mailSender.send(message);
            log.info("Kyc status email sent to {}", recipientEmail);
        }catch (Exception e){
            log.error("Error while sending mail:",e);
            throw new ApiException("Error while sending mail");
        }
    }
}
