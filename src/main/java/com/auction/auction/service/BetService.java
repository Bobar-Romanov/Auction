package com.auction.auction.service;


import com.auction.auction.repo.*;
import com.auction.auction.support.ResponseBet;
import com.auction.auction.models.Bet;
import com.auction.auction.models.Lot;
import com.auction.auction.models.Subscribe;
import com.auction.auction.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Locale;

@Service
public class BetService {
    @Autowired
    BetRepo betRepo;
    @Autowired
    LotRepo lotRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserService userService;
    @Autowired
    SubscribeRepo subscribeRepo;
    @Autowired
    CommentRepo commentRepo;
    @Autowired
    LotService lotService;
    @Autowired
    MailSender mailSender;
    @Autowired
    MessageSource messageSource;

    public String lastBet(Long lotId){
        try {
            return betRepo.lastBet(lotId).get(0);
        }catch (IndexOutOfBoundsException e){
            return userRepo.getById(lotRepo.lotById(lotId).getOwnerId()).getUsername();
        }
    }

    public String idFromJson(String json){
        String[] res = json.split("\"");
        return  res[3];
    }
    public String ValueFromJson(String json){
        String[] res = json.split("\"");
        return  res[7];
    }

    public ResponseBet addNewBet(String lotId, String value, String username) {

        User curUser = userRepo.findByUsername(username);
        int betValue = Integer.parseInt(value);
        Long longLotId = Long.parseLong(lotId);
        Lot curLot = lotRepo.lotById(longLotId);

        if(betValue >= curLot.getRedemptionPrice()){

            if(lotService.buyingProcess(curLot, curUser, curLot.getRedemptionPrice())) {
                return new ResponseBet(true, Integer.toString(curLot.getRedemptionPrice()), username, messageSource.getMessage("betservice.buy",null, new Locale("ru")));
            }else {
                return new ResponseBet(false, null, null, null);
            }
        }

        Bet newBet = new Bet(longLotId,curUser.getId(), betValue);
        curLot.setCurrentPrice(betValue);

        lotRepo.save(curLot);
        betRepo.save(newBet);
        mailSender.SendNotifBet(subscribeRepo.getSubEmailsByLotId(longLotId), longLotId, curUser.getEmail());

       return new ResponseBet(true, value, username, null);

    }

    public String checkBet(Long id, User curUser, String value){
        try {
            int betValue = Integer.parseInt(value);
            Lot curLot =  lotRepo.lotById(id);

            if(curUser.getId().equals(curLot.getOwnerId())){
                return messageSource.getMessage("betservice.WrongBet",null, new Locale("ru"));
            }
            if(betValue <= curLot.getCurrentPrice()){
                return messageSource.getMessage("betservice.UASeller",null, new Locale("ru"));
            }
            if(betValue >= curUser.getBalance()){
                return messageSource.getMessage("betservice.NEB",null, new Locale("ru"));
            }
            if(!curLot.isActive()){
                return messageSource.getMessage("betservice.alreadybuy",null, new Locale("ru"));
            }

        }catch (NumberFormatException e){
            return messageSource.getMessage("betservice.WrongBet",null, new Locale("ru"));
        }

        return null;
    }

}
