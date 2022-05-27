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
    @Autowired
    MailSender mailSender;
    @Autowired
    SubscribeService subscribeService;

    @Value("${upload.path}")
    private String uploadPath;

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
        subscribeService.subscribe(lot.getId(), userId);

        return "redirect:/auction/home";
    }
    public Lot getById(Long id){return lotRepo.getById(id);}

    public Page<Lot> activeLotsPage(Pageable pageable){
        return lotRepo.activeLotsPage(pageable);
    }
    public void deleteById(Long lotId){
        lotRepo.deleteById(lotId);
    }

    public Page<Lot> searchPage(String search, Pageable pageable) {
        return lotRepo.PagefindByNameContains(search,pageable);
    }

    public boolean buyingProcess(Lot curLot, User newOwner, int betValue){
            User oldOwner = userRepo.getById(curLot.getOwnerId());
            if(userService.balaceManage(curLot.getOwnerId(),newOwner.getId(), betValue)) {
            mailSender.SendNoticUSoldIt(oldOwner.getEmail(), curLot.getName());
            curLot.setCurrentPrice(betValue);
            curLot.setActive(false);
            curLot.setOwnerId(newOwner.getId());
            mailSender.SendNotifSomeOneBuy(subscribeRepo.getSubEmailsByLotId(curLot.getId()), curLot.getName(), newOwner.getEmail());
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
               if(bets.isEmpty()){
                   mailSender.SendNotifNBB(userRepo.getById(lot.getOwnerId()).getEmail(), lot.getName());
                   lotRepo.delete(lot);
               }
               for(Bet bet : bets){
                   if(buyingProcess(lot, userRepo.getById(bet.getOwnerId()), bet.getPrice())){
                       mailSender.SendNotifUBuyIt(userRepo.getById(bet.getOwnerId()).getEmail(),lot.getName());
                       break;
                   }
                   mailSender.SendNotifNotEnoughBalance(userRepo.getById(bet.getOwnerId()).getEmail(), lot.getName());
               }
            }
        }

    }

    public ArrayList<Lot> findByActiveFalseAndOwnerId(Long id) {
        return lotRepo.findByActiveFalseAndOwnerId(id);
    }
}
