package com.auction.auction.forms;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

    @Autowired
    MessageSource messageSource;

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
                errors.rejectValue("startPrice", "", messageSource.getMessage("lotvalid.rightprice",null, new Locale("ru")));
            }
            if(redemptionPricePrice <= startPrice){
                errors.rejectValue("redemptionPrice", "", messageSource.getMessage("lotvalid.right.redemption",null, new Locale("ru")));
            }
            if(parseDate.isBefore(LocalDateTime.now())){
                errors.rejectValue("endDate", "", messageSource.getMessage("lotvalid.right.date",null, new Locale("ru")));
            }
            if(LocalDateTime.now().plusMonths(3).isBefore(parseDate)){
                errors.rejectValue("endDate", "", messageSource.getMessage("lotvalid.right.date.3mounth",null, new Locale("ru")));
            }
            if(maimImg.getSize() > 1048575){
                errors.rejectValue("mainImg", "", messageSource.getMessage("lotvalid.right.size",null, new Locale("ru")));
            }
            if(imgName != null){
                String extension = FilenameUtils.getExtension(maimImg.getOriginalFilename());
            if(!extension.equals("png") && !extension.equals("jpg") && !extension.equals("gif") && !extension.equals("jpeg")){
                errors.rejectValue("mainImg", "", messageSource.getMessage("lotvalid.right.ext",null, new Locale("ru")));
            }}
            try{
            if(imgs.length != 0){
                for(MultipartFile img : imgs){
                    if(img.getSize() > 1048576){
                        errors.rejectValue("images", "", messageSource.getMessage("lotvalid.right.size",null, new Locale("ru")));
                    }
                    String imgGetName = img.getOriginalFilename();
                    if(imgGetName != null){
                        String extension = FilenameUtils.getExtension(imgGetName);
                        if(!extension.equals("png") && !extension.equals("jpg") && !extension.equals("gif") && !extension.equals("jpeg")){
                            errors.rejectValue("images", "",  messageSource.getMessage("lotvalid.right.ext",null, new Locale("ru")));
                        }}
                }
            }}catch (NullPointerException e){

            }
        }catch (NumberFormatException e){
            errors.rejectValue("startPrice", "", messageSource.getMessage("lotvalid.rightprice",null, new Locale("ru")));
            errors.rejectValue("redemptionPrice", "", messageSource.getMessage("lotvalid.rightprice",null, new Locale("ru")));
        }

    }
}
