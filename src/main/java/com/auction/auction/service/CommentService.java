package com.auction.auction.service;

import com.auction.auction.models.Comment;
import com.auction.auction.models.User;
import com.auction.auction.repo.CommentRepo;
import com.auction.auction.repo.LotRepo;
import com.auction.auction.repo.SubscribeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CommentService {
    @Autowired
    CommentRepo commentRepo;
    @Autowired
    LotRepo lotRepo;
    @Autowired
    SubscribeRepo subscribeRepo;
    @Autowired
    MailSender mailSender;

    public String saveComm(Long lotId, String comment, User user){
        if(lotRepo.getById(lotId).isActive() && !comment.isBlank()) {
            Comment comment1 = new Comment(lotId, comment, user.getUsername());
            commentRepo.save(comment1);
            ArrayList<String> emails = subscribeRepo.getSubEmailsByLotId(lotId);
            mailSender.SendNotifComm(emails, lotId, user.getEmail());

        }
        return "redirect:/auction/home" + lotId;
    }
    public ArrayList<Comment> findByLotId(Long lotId){
        return commentRepo.findByLotId(lotId);
    }
}
