package com.auction.auction.forms;


import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.Locale;

public class LotForm {
    @NotBlank
    @Max(50)
    private String name;

    @Max(500)
    private String description;


    private String startPrice;


    private String redemptionPrice;

    @NotEmpty
    private MultipartFile mainImg;

    private MultipartFile[] images;

    @NotBlank
    private String endDate;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public String getRedemptionPrice() {
        return redemptionPrice;
    }

    public void setRedemptionPrice(String redemptionPrice) {
        this.redemptionPrice = redemptionPrice;
    }

    public MultipartFile getMainImg() {
        return mainImg;
    }

    public void setMainImg(MultipartFile mainImg) {
        this.mainImg = mainImg;
    }

    public MultipartFile[] getImages() {
        return images;
    }

    public void setImages(MultipartFile[] images) {
        this.images = images;
    }
}
