package com.auction.auction.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegController {

    @GetMapping("/auction")
    public String auction(Model model) {
        return "wellCUM";
    }

    @GetMapping("/auction/regestration")
    public String Regestration(Model model) {
        return "regPage";
    }
}
