package com.auction.auction.repo;


import com.auction.auction.models.Lot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotRepo extends JpaRepository<Lot, Long> {
}
