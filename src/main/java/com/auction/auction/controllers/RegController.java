package com.auction.auction.controllers;

import com.auction.auction.models.User;
import com.auction.auction.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.auction.auction.service.UserService;

@Controller
public class RegController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/auction")
    public String auction(Model model) {
        return "login";
    }

    @GetMapping("/auction/registration")
    public String registration()
    {
        return "registration";
    }

    @PostMapping("/auction/registration")
    public String addUser(@RequestParam String email, @RequestParam String username, @RequestParam String password, Model model) {
        return userService.addUser(username, email, password, model);
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActived = userService.activateUser(code);
        if(isActived){
            model.addAttribute("message","Активация прошла успешно!");
        } else {
            model.addAttribute("message","Что-то пошло не так...");
        }
        return "login";
    }
}
