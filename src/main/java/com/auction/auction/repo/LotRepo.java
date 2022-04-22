package com.auction.auction.repo;


import com.auction.auction.models.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;


public interface LotRepo extends JpaRepository<Lot, Long> {

   }
