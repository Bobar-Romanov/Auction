package com.auction.auction.controllers;

import com.auction.auction.models.User;
import com.auction.auction.service.BetService;
import net.minidev.json.JSONObject;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;


@Controller
public class BetController {

    @Autowired
    BetService betService;

    @MessageMapping("/auction/home/{id}/makeBet")
    @SendTo("/topic/bet/{id}")
    public String addBet(@Payload String jsonString, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println(betService.idFromJson(jsonString));
        System.out.println(betService.ValueFromJson(jsonString));
        System.out.println(headerAccessor.getUser().getName());
        return betService.ValueFromJson(jsonString);
    }

}
