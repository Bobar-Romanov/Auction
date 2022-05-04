package com.auction.auction.controllers;

import com.auction.auction.models.User;
import com.auction.auction.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SubController {

    @Autowired
    private SubscribeService subscribeService;

    @RequestMapping(value = "auction/home/{id}/checkSub", method = RequestMethod.GET, produces = {"text/html;charset=UTF-8"})
    public @ResponseBody
    String checkSub(@PathVariable(value = "id") long id, @AuthenticationPrincipal User user ) {

        return subscribeService.checkSubscribe(id, user.getId());
    }
    @RequestMapping(value = "auction/home/{id}/Sub", method = RequestMethod.GET, produces = {"text/html;charset=UTF-8"})
    public @ResponseBody String sub(@PathVariable(value = "id") long id, @AuthenticationPrincipal User user ) {
        return subscribeService.subscribe(id, user.getId());
    }
    @RequestMapping(value = "auction/home/{id}/unSub", method = RequestMethod.GET, produces = {"text/html;charset=UTF-8"})
    public @ResponseBody String unSub(@PathVariable(value = "id") long id, @AuthenticationPrincipal User user ) {
        return subscribeService.unSub(id, user.getId());
    }
}
