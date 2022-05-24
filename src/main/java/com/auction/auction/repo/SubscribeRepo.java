package com.auction.auction.repo;


import com.auction.auction.models.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;


public interface SubscribeRepo extends JpaRepository<Subscribe, Long> {

    boolean existsByLotIdAndUserId(Long lotId, Long userId);
    Subscribe getByLotIdAndUserId(Long lotId, Long userId);
    ArrayList<Subscribe> getByLotId(Long lotId);

    @Query(value = "SELECT u.email FROM User u LEFT JOIN Subscribe s ON u.id = s.userId WHERE s.lotId = ?1")
    ArrayList<String> getSubEmailsByLotId(Long lotId);
}
