package com.auction.auction.service;

import com.auction.auction.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MailSender {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MessageSource messageSource;

    @Value("${spring.mail.username}")
    private String username;

    public void SendRegMail(User user){
        String message = String.format(
                messageSource.getMessage("reg.sendmail",null, Locale.getDefault()) + "http://localhost:8080/test/%s",
                user.getUsername(),
                user.getActivationCode()
        );
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(messageSource.getMessage("reg.sendmail.subject",null, Locale.getDefault()));
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }



}
