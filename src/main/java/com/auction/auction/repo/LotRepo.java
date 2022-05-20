package com.auction.auction.repo;


import com.auction.auction.models.Comment;
import com.auction.auction.models.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;


public interface LotRepo extends JpaRepository<Lot, Long> {
      ArrayList<Lot> findByActiveFalseAndOwnerId(Long id);

      @Query(value = "SELECT l FROM Lot l WHERE l.active = TRUE ORDER BY l.startDate desc")
      ArrayList<Lot> activeLots();

      @Query(value = "SELECT l FROM Lot l LEFT JOIN Subscribe s ON s.lotId = l.id WHERE s.userId = ?1")
      ArrayList<Lot> favoriteLots(Long userId);

      @Query(value = "SELECT u.username FROM User u LEFT JOIN Lot l ON u.id = l.ownerId WHERE l.id = ?1 ")
      String seller(Long lotId);


      @Query(value = "SELECT l FROM Lot l WHERE l.active = TRUE AND l.name LIKE %?1% ")
      ArrayList<Lot> findByNameContains(String str);

      @Query(value = "SELECT l FROM Lot l WHERE l.active = TRUE AND l.description LIKE %?1% ")
      ArrayList<Lot> findByDescriptionContains(String str);

      @Query(value = "SELECT l FROM Lot l WHERE l.id = ?1")
      Lot lotById(Long id);


}
