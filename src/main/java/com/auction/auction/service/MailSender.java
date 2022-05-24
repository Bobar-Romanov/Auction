package com.auction.auction.service;

import com.auction.auction.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                messageSource.getMessage("reg.sendmail",null, new Locale("ru")) + "http://localhost:8080//activate/%s",
                user.getUsername(),
                user.getActivationCode()
        );
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(messageSource.getMessage("reg.sendmail.subject",null, new Locale("ru")));
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
    public void SendNotifComm(ArrayList<String> emails, Long lotId, String ignore){
        if(emails.isEmpty()){
            return;
        }
        String message = String.format(
                messageSource.getMessage("",null, new Locale("ru")) + "http://localhost:8080/auction/home/%s",
              lotId
        );
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setSubject(messageSource.getMessage("",null, new Locale("ru")));
        mailMessage.setText(message);
        for(String email : emails){
            if(!email.equals(ignore)){
               mailMessage.setTo(email);
               mailSender.send(mailMessage);
            }
        }
    }
    public void SendNotifBet(ArrayList<String> emails, Long lotId, String ignore){
        if(emails.isEmpty()){
            return;
        }
        String message = String.format(
                messageSource.getMessage("",null, new Locale("ru")) + "http://localhost:8080/auction/home/%s",
                lotId
        );
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setSubject(messageSource.getMessage("",null, new Locale("ru")));
        mailMessage.setText(message);
        for(String email : emails){
            if(!email.equals(ignore)){
                mailMessage.setTo(email);
                mailSender.send(mailMessage);
            }
        }
    }
    public void SendNotifSomeOneBuy(ArrayList<String> emails,String lotName, String ignore){
        if(emails.isEmpty()){
            return;
        }
        String message = String.format(
                messageSource.getMessage("",null, new Locale("ru")),
                lotName
        );
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setSubject(messageSource.getMessage("",null, new Locale("ru")));
        mailMessage.setText(message);
        for(String email : emails){
            if(!email.equals(ignore)){
                mailMessage.setTo(email);
                mailSender.send(mailMessage);
            }
        }
    }
    public void SendNotifUBuyIt(String email, String lotName){
        String message = String.format(
                messageSource.getMessage("",null, new Locale("ru")),
                lotName
        );
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setSubject(messageSource.getMessage("",null, new Locale("ru")));
        mailMessage.setText(message);
        mailMessage.setTo(email);
        mailSender.send(mailMessage);

    }
    public void SendNotifNotEnoughBalance(String email, String lotName){
        String message = String.format(
                messageSource.getMessage("",null, new Locale("ru")),
                lotName
        );
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setSubject(messageSource.getMessage("",null, new Locale("ru")));
        mailMessage.setText(message);
        mailMessage.setTo(email);
        mailSender.send(mailMessage);
    }
    public void SendNoticUSoldIt(String email, String lotName){
        String message = String.format(
                messageSource.getMessage("",null, new Locale("ru")),
                lotName
        );
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setSubject(messageSource.getMessage("",null, new Locale("ru")));
        mailMessage.setText(message);
        mailMessage.setTo(email);
        mailSender.send(mailMessage);
    }



}
