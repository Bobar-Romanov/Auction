package com.auction.auction.controllers;

import com.auction.auction.models.Lot;
import com.auction.auction.models.User;
import com.auction.auction.repo.LotRepo;
import com.auction.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Controller
public class MainController {


    @Autowired
    private UserService userService;

    @Autowired
    private LotRepo lotRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/auction/home")
    public String HomePage(Model model) {
        Iterable<Lot> lots = lotRepo.findAll();
        model.addAttribute("lots", lots);
        return "homePage";
    }
    @GetMapping("/auction/home/add")
    public String addLot() {
        User curUser = userService.getCurrentUser();
        return "addLot";
    }
    @PostMapping ("/auction/home/add")
    public String addLotPost(@RequestParam String name,
                             @RequestParam String description,
                             @RequestParam int startPrice,
                             @RequestParam int redemptionPrice,
                             @RequestParam String endDate,
                             @RequestParam("mainImg") MultipartFile mainImg,
                             Model model) throws IOException {
        User curUser = userService.getCurrentUser();
        Lot lot = new Lot(name, description, startPrice, redemptionPrice, curUser.getUsername(),endDate);
        File uploadDir = new File(uploadPath);
        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }
        String uuidFile = UUID.randomUUID().toString();
        String res = uuidFile + "." + mainImg.getOriginalFilename();
        mainImg.transferTo(new File(uploadPath + "/" + res));
        lot.setMainImg(res);
        lotRepo.save(lot);
        return "redirect:/auction/home";
    }

    @GetMapping("/auction/home/{id}")
    public String lotById(@PathVariable(value = "id") long id, Model model) {
        Optional<Lot> blog = lotRepo.findById(id);
        ArrayList<Lot> res = new ArrayList<>();
        blog.ifPresent(res::add);
        model.addAttribute("lot", res);
        return "lot";
    }

}