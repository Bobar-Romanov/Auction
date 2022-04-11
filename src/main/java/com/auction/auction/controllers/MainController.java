package com.auction.auction.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/auction/homePage")
    public String HomePage(Model model) {
        model.addAttribute("name", "Ivak");
        return "homePage";
    }

}