package com.auction.auction.forms;

import com.auction.auction.repo.UserRepo;
import com.auction.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserFormValidator implements Validator {


    @Autowired
    private UserRepo userRepo;
    @Autowired
    MessageSource messageSource;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserForm form = (UserForm) target;
        String email = form.getEmail();
        String username = form.getUsername();
        String password = form.getPassword();
        Pattern p = Pattern.compile("[^a-z0-9A-Z.,!_-]");
        Matcher usernamematch = p.matcher(username);
        if(usernamematch.find()){

            errors.rejectValue("username", "", messageSource.getMessage("uservalid.username.sym",null, new Locale("ru")));
        }
        Matcher passwordematch = p.matcher(password);
        if(passwordematch.find()){
            errors.rejectValue("password", "", messageSource.getMessage("uservalid.password.sym",null, new Locale("ru")));
        }
        if (userRepo.existsByEmail(email)) {
            errors.rejectValue("email", "", messageSource.getMessage("uservalid.email.alreadyuse",null, new Locale("ru")));
        }
        if(userRepo.existsByUsername(username)){
            errors.rejectValue("username", "", messageSource.getMessage("uservalid.username.alredyuse",null, new Locale("ru")));
        }

    }
}
