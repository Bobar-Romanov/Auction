package com.auction.auction.service;


import com.auction.auction.forms.ResponseBet;
import com.auction.auction.models.Bet;
import com.auction.auction.models.Lot;
import com.auction.auction.models.User;
import com.auction.auction.repo.BetRepo;
import com.auction.auction.repo.LotRepo;
import com.auction.auction.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
