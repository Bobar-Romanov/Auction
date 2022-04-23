package com.auction.auction.repo;


import com.auction.auction.models.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribeRepo extends JpaRepository<Subscribe, Long> {

    boolean existsByLotIdAndUserId(Long lotId, Long userId);
}
