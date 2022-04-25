package com.auction.auction.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long lotId, ownerId;
    private int price;


    public Bet(Long lotId, Long ownerId, int price) {
        this.lotId = lotId;
        this.ownerId = ownerId;
        this.price = price;
    }
    public Bet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
