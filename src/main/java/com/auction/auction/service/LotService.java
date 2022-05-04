package com.auction.auction.service;



import com.auction.auction.models.Lot;
import com.auction.auction.repo.LotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LotService  {
    @Autowired
    LotRepo lotRepo;

    public ArrayList<Lot> getActiveLots(){
       return lotRepo.activeLots();
    }

    public ArrayList<Lot> getFavoriteLots(Long userId){
        return lotRepo.favoriteLots(userId);
    }
}
