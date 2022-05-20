package com.auction.auction.service;


import com.auction.auction.forms.ResponseBet;
import com.auction.auction.models.Bet;
import com.auction.auction.models.Lot;
import com.auction.auction.models.Subscribe;
import com.auction.auction.models.User;
import com.auction.auction.repo.BetRepo;
import com.auction.auction.repo.LotRepo;
import com.auction.auction.repo.SubscribeRepo;
import com.auction.auction.repo.UserRepo;
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
            userService.balaceManage(curLot.getOwnerId(), curUser.getId(), curLot.getRedemptionPrice());
            curLot.setCurrentPrice(curLot.getRedemptionPrice());
            curLot.setActive(false);
            curLot.setOwnerId(curUser.getId());
            deleteBetByLotId(longLotId);
            deleteSubByLotId(longLotId);
            lotRepo.save(curLot);
            return new ResponseBet(true, Integer.toString(curLot.getRedemptionPrice()), username, "лот куплен" );
        }
        Bet newBet = new Bet(longLotId,curUser.getId(), betValue);
        curLot.setCurrentPrice(betValue);
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

    public void deleteSubByLotId(Long lotId){
        ArrayList<Subscribe> res = subscribeRepo.getByLotId(lotId);
        if(res.isEmpty()){
            return;
        }
        for(Subscribe sub : res){
            subscribeRepo.delete(sub);
        }
    }
    public void deleteBetByLotId(Long lotId){
        ArrayList<Bet> res = betRepo.getByLotId(lotId);
        if(res.isEmpty()){
            return;
        }
        for(Bet bet : res){
            betRepo.delete(bet);
        }
    }
}
