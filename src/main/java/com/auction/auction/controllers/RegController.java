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

        User user = new User(username, email, password);

        if(userRepo.existsByEmail(email)){
            model.addAttribute("emailError", "Данный email адрес уже зарегистрирован");
            model.addAttribute("email", email);
            model.addAttribute("username", username);
            return "registration";
        }
        if(userRepo.existsByUsername(username)){
            model.addAttribute("nameError", "Пользователь с таким именем уже существует");
            model.addAttribute("email", email);
            model.addAttribute("username", username);
            return "registration";
        }

        if (!userService.saveUser(user)){
            return "registration";
        }
        return "redirect:/auction";
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
