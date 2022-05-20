package com.auction.auction.controllers;

import com.auction.auction.forms.ResponseBet;
import com.auction.auction.models.User;
import com.auction.auction.service.BetService;
import net.minidev.json.JSONObject;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class BetController {

    @Autowired
    BetService betService;

    @MessageMapping("/auction/home/{id}/makeBet")
    @SendTo("/topic/bet/{id}")
    public ResponseBet addBet(@Payload String jsonString, SimpMessageHeaderAccessor headerAccessor) {

        return betService.addNewBet(betService.idFromJson(jsonString),betService.ValueFromJson(jsonString),headerAccessor.getUser().getName());
    }

    @RequestMapping(value = "auction/home/{id}/checkBet", method = RequestMethod.GET, produces = {"text/html;charset=UTF-8"})
    public @ResponseBody
    String checkBet(@PathVariable(value = "id") long id, @AuthenticationPrincipal User user, @RequestParam String curBet) {
        return betService.checkBet(id, user, curBet);
    }

}
