package com.auction.auction.service;



import com.auction.auction.models.Image;
import com.auction.auction.models.Lot;
import com.auction.auction.repo.ImageRepo;
import com.auction.auction.repo.LotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class LotService  {
    @Autowired
    LotRepo lotRepo;
    @Autowired
    ImageRepo imageRepo;
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
                       String endDate, MultipartFile mainImg, MultipartFile[] images, Model model) throws IOException {

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

    public ArrayList<Lot> search(String search) {
        ArrayList<Lot> byName = lotRepo.findByNameContains(search);
        ArrayList<Lot> byDesc = lotRepo.findByDescriptionContains(search);

        byDesc.removeAll(byName);
        byName.addAll(byDesc);

        return byName;
    }
}
