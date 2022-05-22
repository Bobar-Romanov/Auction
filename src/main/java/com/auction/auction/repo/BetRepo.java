package com.auction.auction.repo;

import com.auction.auction.models.Bet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;


public interface BetRepo extends JpaRepository<Bet, Long> {

    @Query(value = "SELECT u.username FROM User u LEFT JOIN Bet b ON u.id = b.ownerId WHERE b.lotId = ?1 ORDER BY b.price DESC")
    ArrayList<String> lastBet(Long lotId);


    ArrayList<Bet> getByLotIdOrderByPriceDesc(Long lotId);


}
