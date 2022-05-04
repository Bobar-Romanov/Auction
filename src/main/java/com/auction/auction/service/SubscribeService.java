package com.auction.auction.service;


import com.auction.auction.models.Subscribe;
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

    public String checkSubscribe(Long lotId, Long userId){
        if(subscribeRepo.existsByLotIdAndUserId(lotId, userId)){
            return "true";
        }
        return "false";
    }
    public String subscribe(Long lotId, Long userId){

        if(subscribeRepo.existsByLotIdAndUserId(lotId, userId)){
            return "false";
        }else{
            Subscribe subscribe = new Subscribe(lotId,userId);
            subscribeRepo.save(subscribe);
            return "true";
        }
    }
    public String unSub(Long lotId, Long userId){
        if(subscribeRepo.existsByLotIdAndUserId(lotId, userId)){
            Subscribe subscribe = subscribeRepo.getByLotIdAndUserId(lotId,userId);
            subscribeRepo.delete(subscribe);
            return "true";
        }else{
            return "false";
        }
    }
}
