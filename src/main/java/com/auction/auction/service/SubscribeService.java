package com.auction.auction.service;


import com.auction.auction.repo.SubscribeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscribeService {
    @Autowired
    SubscribeRepo subscribeRepo;
    @Autowired
    MailSender mailSender;

    public void notificationSend(String email, Long lotId, String username){

        String message = String.format(
                "Дорогой! %s \n" +
                        "Какая-то суета ежи: http://localhost:8080/aution/home/%s",
               username,
                lotId
        );

        mailSender.Send(email, "Notification", message);
    }

}
