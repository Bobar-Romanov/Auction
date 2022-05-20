package com.auction.auction.repo;


import com.auction.auction.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface CommentRepo extends JpaRepository<Comment, Long> {
        ArrayList<Comment> findByLotId(Long id);
}
