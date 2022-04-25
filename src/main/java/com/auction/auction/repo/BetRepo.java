package com.auction.auction.repo;

import com.auction.auction.models.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BetRepo extends JpaRepository<Bet, Long> {
}
