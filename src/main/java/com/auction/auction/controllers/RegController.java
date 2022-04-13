package com.auction.auction.controllers;

import com.auction.auction.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.auction.auction.service.UserService;

@Controller
public class RegController {

    @Autowired
    private UserService userService;

    @GetMapping("/auction")
    public String auction(Model model) {
        return "login";
    }

    @RequestMapping("/auction")
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        model.addAttribute("error", error != null);
        System.out.println(error);
        System.out.println("zxc");
        model.addAttribute("logout", logout != null);
        System.out.println(logout);
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

        if (!userService.saveUser(user)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }

        return "redirect:/";
    }
}
