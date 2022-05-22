package com.auction.auction.forms;

import com.auction.auction.repo.UserRepo;
import com.auction.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserFormValidator implements Validator {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;

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

            errors.rejectValue("username", "", "Логин содержит недопустимые символы");
        }
        Matcher passwordematch = p.matcher(password);
        if(passwordematch.find()){
            errors.rejectValue("password", "", "Пароль содержит недопустимые символы");
        }
        if (userRepo.existsByEmail(email)) {
            errors.rejectValue("email", "", "Данный емайл уже зарегестрирован");
        }
        if(userRepo.existsByUsername(username)){
            errors.rejectValue("username", "", "Пользователь с таким именем уже существует");
        }
    }
}
