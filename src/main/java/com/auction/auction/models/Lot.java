package com.auction.auction.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean active;

    private String name;
    private String description;

    private int currentPrice, redemptionPrice;

    private String owner;

    private LocalDateTime startDate, endDate;

    public Lot(String name, String description, int currentPrice, int redemptionPrice, String owner, LocalDateTime endDate) {
        this.active = true;
        this.name = name;
        this.description = description;
        this.currentPrice = currentPrice;
        this.redemptionPrice = redemptionPrice;
        this.owner = owner;
        this.startDate = LocalDateTime.now();
        this.endDate = endDate;

    }

    public Lot() {
    }

    public String getStartDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String stringDate = this.startDate.format(formatter);
        return stringDate;
    }
    public String getEndDateString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String stringDate = this.endDate.format(formatter);
        return stringDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getRedemptionPrice() {
        return redemptionPrice;
    }

    public void setRedemptionPrice(int redemptionPrice) {
        this.redemptionPrice = redemptionPrice;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
