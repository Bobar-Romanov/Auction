package com.auction.auction.service;


import com.auction.auction.repo.*;
import com.auction.auction.support.ResponseBet;
import com.auction.auction.models.Bet;
import com.auction.auction.models.Lot;
import com.auction.auction.models.Subscribe;
import com.auction.auction.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
                return new ResponseBet(true, Integer.toString(curLot.getRedemptionPrice()), username, "лот куплен");
            }else {
                return new ResponseBet(false, null, null, null);
            }
        }

        Bet newBet = new Bet(longLotId,curUser.getId(), betValue);
        curLot.setCurrentPrice(betValue);
        mailSender.SendNotifBet(subscribeRepo.getSubEmailsByLotId(longLotId), longLotId, curUser.getEmail());
        lotRepo.save(curLot);
        betRepo.save(newBet);

       return new ResponseBet(true, value, username, null);

    }

    public String checkBet(Long id, User curUser, String value){
        try {
            int betValue = Integer.parseInt(value);
            Lot curLot =  lotRepo.lotById(id);

            if(curUser.getId().equals(curLot.getOwnerId())){
                return "Вы являетесь продавцом";
            }
            if(betValue <= curLot.getCurrentPrice()){
                return "Ставка - положительное число, не менее текущей ставки";
            }
            if(betValue >= curUser.getBalance()){
                return "На балансе недостаточно средств";
            }
            if(!curLot.isActive()){
                return "Лот уже куплен";
            }

        }catch (NumberFormatException e){
            return "Ставка - положительное число, не менее текущей ставки";
        }

        return null;
    }

}
