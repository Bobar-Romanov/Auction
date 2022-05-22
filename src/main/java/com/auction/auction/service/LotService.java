package com.auction.auction.service;



import com.auction.auction.models.Bet;
import com.auction.auction.models.Image;
import com.auction.auction.models.Lot;
import com.auction.auction.models.User;
import com.auction.auction.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Service
@EnableScheduling
public class LotService  {
    @Autowired
    LotRepo lotRepo;
    @Autowired
    ImageRepo imageRepo;
    @Autowired
    BetRepo betRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserService userService;
    @Autowired
    SubscribeRepo subscribeRepo;
    @Autowired
    CommentRepo commentRepo;

    @Value("${upload.path}")
    private String uploadPath;

    public ArrayList<Lot> getActiveLots(){
       return lotRepo.activeLots();
    }

    public ArrayList<Lot> getFavoriteLots(Long userId){
        return lotRepo.favoriteLots(userId);
    }
    public String getSeller(Long lotId){
        return lotRepo.seller(lotId);
    }
    public String addLot(String name, String description, int startPrice, int redemptionPrice, Long userId,
                       String endDate, MultipartFile mainImg, MultipartFile[] images) throws IOException {

        Lot lot = new Lot(name, description, startPrice, redemptionPrice, userId, endDate);

        File uploadDir = new File(uploadPath);
        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }

        String uuidFile = UUID.randomUUID().toString();
        String res = uuidFile + "." + mainImg.getOriginalFilename();
        mainImg.transferTo(new File(uploadPath + "/" + res));

        lot.setMainImg(res);

        lotRepo.save(lot);

        if(images != null){
            for(MultipartFile img : images){
                String uuidImg = UUID.randomUUID().toString();
                String resImg = uuidImg + "." + img.getOriginalFilename();
                img.transferTo(new File(uploadPath + "/" + resImg));
                Image image = new Image(lot.getId(), resImg);
                imageRepo.save(image);
            }
        }


        return "redirect:/auction/home";
    }


    public Page<Lot> activeLotsPage(Pageable pageable){
        return lotRepo.activeLotsPage(pageable);
    }


    public Page<Lot> searchPage(String search, Pageable pageable) {
        return lotRepo.PagefindByNameContains(search,pageable);
    }

    public boolean buyingProcess(Lot curLot, User newOwner, int betValue){

            if(userService.balaceManage(curLot.getOwnerId(),newOwner.getId(), betValue)) {
            curLot.setCurrentPrice(betValue);
            curLot.setActive(false);
            curLot.setOwnerId(newOwner.getId());
            try {
                subscribeRepo.deleteAll(subscribeRepo.getByLotId(curLot.getId()));
                betRepo.deleteAll(betRepo.getByLotIdOrderByPriceDesc(curLot.getId()));
                commentRepo.deleteAll(commentRepo.findByLotId(curLot.getId()));
            } catch (IllegalArgumentException e) {

            }
            lotRepo.save(curLot);
            return true;
        }
            return false;
    }

    @Scheduled(fixedDelay = 60000)
    public void auctionProcess() throws InterruptedException {

        ArrayList<Lot> lots = lotRepo.getOutTimedLots(LocalDateTime.now());
        if(!lots.isEmpty()){
            for(Lot lot : lots){
               ArrayList<Bet> bets = betRepo.getByLotIdOrderByPriceDesc(lot.getId());
               for(Bet bet : bets){
                   if(buyingProcess(lot, userRepo.getById(bet.getOwnerId()), bet.getPrice())){
                       break;
                   }
               }
            }
        }

    }

}
