package com.auction.auction.controllers;

import com.auction.auction.forms.UserForm;
import com.auction.auction.forms.UserFormValidator;
import com.auction.auction.models.User;
import com.auction.auction.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.auction.auction.service.UserService;

import javax.validation.Valid;

@Controller
public class RegController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserFormValidator userFormValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(userFormValidator);
    }


    @GetMapping("/auction")
    public String auction(Model model) {
        return "login";
    }

    @GetMapping("/auction/registration")
    public String registration(Model model)
    {
        model.addAttribute(new UserForm());
        return "registration";
    }

    @PostMapping("/auction/registration")
    public String addUser(@ModelAttribute @Valid UserForm userForm, BindingResult result) {

        if (result.hasErrors()) {
            return "registration";
        } else {
            return userService.addUser(userForm.getUsername(), userForm.getEmail(), userForm.getPassword());
        }
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        userService.activateUser(code);
        return "login";
    }
}
