package com.geminibot.geminibot.services;

import com.geminibot.geminibot.entities.postgres.User;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    private final JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    public void sendActivationEmail(User user) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMessage = "<a href=\"localhost:8080/user/activate/" + user.getEmailVerificationToken() + "\">Click localhost:8080/user/activate/" + user.getEmailVerificationToken() + " to activate your account</a>";
        helper.setText(htmlMessage, true);
        helper.setTo(user.getEmail());
        helper.setSubject("Confirm Email for Gemini Bot");
        helper.setFrom("application@gemini-bot.com");
        javaMailSender.send(mimeMessage);
    }
}
