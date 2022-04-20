package com.auction.auction.service;



import com.auction.auction.repo.LotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LotService  {
    @Autowired
    LotRepo lotRepo;

}
