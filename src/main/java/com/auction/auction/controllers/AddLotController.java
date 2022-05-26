package com.auction.auction.controllers;


import com.auction.auction.forms.LotForm;
import com.auction.auction.forms.LotFormValidation;
import com.auction.auction.forms.UserForm;
import com.auction.auction.forms.UserFormValidator;
import com.auction.auction.models.User;
import com.auction.auction.repo.LotRepo;
import com.auction.auction.service.LotService;
import com.auction.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AddLotController {

    @Autowired
    private LotService lotService;

    @Autowired
    private LotFormValidation validation;

    @GetMapping("/auction/home/add")
    public String addLot(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute(new LotForm());
        return "addLot";
    }

    @PostMapping("/auction/home/add")
    public String addLotPost(@ModelAttribute  @Valid LotForm lotForm, BindingResult result, @AuthenticationPrincipal User user)
            throws IOException {
        validation.validate(lotForm, result);
        if (result.hasErrors()) {
            return "addLot";
        } else {
           return lotService.addLot(lotForm.getName(), lotForm.getDescription(), Integer.parseInt(lotForm.getStartPrice())
                   ,Integer.parseInt(lotForm.getRedemptionPrice()),user.getId(), lotForm.getEndDate(), lotForm.getMainImg(), lotForm.getImages());

        }
    }

    @ModelAttribute(name = "user")
    public User currentUser(@AuthenticationPrincipal User user) {
        return user;
    }

    @ControllerAdvice
    public class FileUploadExceptionAdvice {

        @ExceptionHandler(MaxUploadSizeExceededException.class)
        public ModelAndView handleMaxSizeException(
                MaxUploadSizeExceededException exc,
                HttpServletRequest request,
                HttpServletResponse response) {

            ModelAndView modelAndView = new ModelAndView("error-size");

            return modelAndView;
        }
    }


}
