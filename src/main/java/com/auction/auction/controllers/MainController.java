package com.auction.auction.controllers;

import com.auction.auction.models.Comment;
import com.auction.auction.models.Image;
import com.auction.auction.models.Lot;
import com.auction.auction.models.User;
import com.auction.auction.repo.CommentRepo;
import com.auction.auction.repo.LotRepo;
import com.auction.auction.repo.SubscribeRepo;
import com.auction.auction.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private LotRepo lotRepo;
    @Autowired
    private LotService lotService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ImgService imgService;
    @Autowired
    private BetService betService;


    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/auction/home")
    public String HomePage(Model model,
                           @PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable) {
                Page<Lot> lotsPages = lotService.activeLotsPage(pageable);
        model.addAttribute("lots", lotsPages);
        ArrayList<String> em = new ArrayList<>();

        return "homePage";
    }


        @PostMapping("/auction/home")
        public String search (@RequestParam String search, Model model,
                              @PageableDefault(sort = {},direction = Sort.Direction.DESC) Pageable pageable){
            Page<Lot> lotsPages = lotService.searchPage(search,pageable);
            model.addAttribute("lots", lotsPages);
            return "homePage";

    }

    @GetMapping("/auction/home/{id}")
    public String lotById(@PathVariable(value = "id") long id, Model model) {

        Optional<Lot> lot = lotRepo.findById(id);
        ArrayList<Lot> res = new ArrayList<>();
        lot.ifPresent(res::add);
        model.addAttribute("lot", res);

        ArrayList<Image> images = imgService.getImagesLotId(id);
        model.addAttribute("images", images);
        model.addAttribute("total", images.size() + 1);
        String seller = lotService.getSeller(id);
        model.addAttribute("seller", seller);
        String lastBet = betService.lastBet(id);
        model.addAttribute("lastBet", lastBet);
        ArrayList<Comment> comms = commentService.findByLotId(id);
        model.addAttribute("comments", comms);

        return "currentLot";
    }

    @PostMapping("/auction/home/{id}/comm")
    public String addcomm(@AuthenticationPrincipal User user,
                          @PathVariable(value = "id") long id,
                          @RequestParam String comment,
                          Model model){




        return commentService.saveComm(id,comment,user);
    }
    @GetMapping("/auction/home/profile")
    public String profile(@AuthenticationPrincipal User user, Model model) {
        ArrayList<Lot> lots = lotRepo.findByActiveFalseAndOwnerId(user.getId());
        model.addAttribute("lots", lots );
        model.addAttribute("user", user );
        return "profile" ;
    }

    @GetMapping("/auction/home/favorite")
    public String favoriteLots(@AuthenticationPrincipal User user, Model model) {
        ArrayList<Lot> lots = lotService.getFavoriteLots(user.getId());
        model.addAttribute("lots", lots);
        return "favorite";
    }

    @ModelAttribute(name = "user")
    public User currentUser(@AuthenticationPrincipal User user) {
        return user;
    }

    @GetMapping("/auction/home/{id}/remove")
    public String removeLot(@PathVariable(value = "id") long id) {           ;
            lotService.deleteById(id);

        return "redirect:/auction/home";
    }


}