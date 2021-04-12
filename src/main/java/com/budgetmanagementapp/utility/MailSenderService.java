package com.budgetmanagementapp.utility;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@AllArgsConstructor
public class MailSenderService {
    private final JavaMailSender mailSender;

    private void send(String to, String subject, String body) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        try {
            InternetAddress addressFrom = new InternetAddress("username", "Budget app team");
            helper.setSubject(subject);
            helper.setTo(to);
            helper.setText(body, true);
            helper.setFrom(addressFrom);

            mailSender.send(message);
        } catch (UnsupportedEncodingException e) {
            log.warn("UnsupportedEncodingException caught while message sending");
        }

    }

    public Map<String, String> sendOtp(String to, String subject, String body) throws MessagingException {
        send(to, subject, body);

        Map<String, String> details = new HashMap<>();
        details.put("to", to);
        details.put("subject", subject);
        details.put("body", body);

        return details;
    }
}
