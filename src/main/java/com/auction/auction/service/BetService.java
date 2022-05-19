package com.auction.auction.service;


import com.auction.auction.repo.BetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BetService {
    @Autowired
    BetRepo betRepo;

    public String idFromJson(String json){
        String[] res = json.split("\"");
        return  res[3];
    }
    public String ValueFromJson(String json){
        String[] res = json.split("\"");
        return  res[7];
    }
}
