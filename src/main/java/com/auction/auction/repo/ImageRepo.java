package com.auction.auction.repo;


import com.auction.auction.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ImageRepo extends JpaRepository<Image, Long> {

    ArrayList<Image> findByLotId(Long lotId);
}
