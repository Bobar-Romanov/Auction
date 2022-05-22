package com.auction.auction.forms;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.apache.commons.io.FilenameUtils;

@Component
public class LotFormValidation implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return LotForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LotForm form = (LotForm) target;

        try {
            int startPrice = Integer.parseInt(form.getStartPrice());
            int redemptionPricePrice = Integer.parseInt(form.getRedemptionPrice());

            MultipartFile maimImg = form.getMainImg();
            String imgName = maimImg.getOriginalFilename();
            MultipartFile[] imgs = form.getImages();


            String date = form.getEndDate();
            date = date.replace('T', ' ');
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.getDefault());
            LocalDateTime parseDate = LocalDateTime.parse(date, formatter);


            if(startPrice < 0 || redemptionPricePrice <= 0){
                errors.rejectValue("startPrice", "", "Указывайте цену верно");
            }
            if(redemptionPricePrice <= startPrice){
                errors.rejectValue("redemptionPrice", "", "Выкупная цена должна быть больше стартовой");
            }
            if(parseDate.isBefore(LocalDateTime.now())){
                errors.rejectValue("endDate", "", "Дата окончания аукциона не ранее сегодня");
            }
            if(LocalDateTime.now().plusMonths(3).isBefore(parseDate)){
                errors.rejectValue("endDate", "", "Аукцион не может длиться более 3х месцев");
            }
            if(maimImg.getSize() > 1048576){
                errors.rejectValue("mainImg", "", "Файл не должен превышать 2 МБ");
            }
            if(imgName != null){
                String extension = FilenameUtils.getExtension(maimImg.getOriginalFilename());
            if(!extension.equals("png") && !extension.equals("jpg") && !extension.equals("gif") && !extension.equals("jpeg")){
                errors.rejectValue("mainImg", "", "Мы можете загружать только картинки");
            }}
            try{
            if(imgs.length != 0){
                for(MultipartFile img : imgs){
                    if(img.getSize() > 1048576){
                        errors.rejectValue("images", "", "Файл не должен превышать 2 МБ");
                    }
                    String imgGetName = img.getOriginalFilename();
                    if(imgGetName != null){
                        String extension = FilenameUtils.getExtension(imgGetName);
                        if(!extension.equals("png") && !extension.equals("jpg") && !extension.equals("gif") && !extension.equals("jpeg")){
                            errors.rejectValue("images", "", "Мы можете загружать только картинки");
                        }}
                }
            }}catch (NullPointerException e){

            }
        }catch (NumberFormatException e){
            errors.rejectValue("startPrice", "", "Указывайте цену верно");
            errors.rejectValue("redemptionPrice", "", "Указывайте цену верно");
        }

    }
}
