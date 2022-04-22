package com.auction.auction.controllers;


import com.auction.auction.repo.LotRepo;
import com.auction.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LotController {

    @Autowired
    private UserService userService;
    @Autowired
    private LotRepo lotRepo;





}
