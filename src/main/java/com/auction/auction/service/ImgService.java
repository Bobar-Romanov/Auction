package com.auction.auction.service;



import com.auction.auction.models.Image;
import com.auction.auction.repo.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ImgService {

    @Autowired
    ImageRepo imageRepo;

    public ArrayList<Image> getImagesLotId(Long lotId){
        return imageRepo.findByLotId(lotId);
    }


}
