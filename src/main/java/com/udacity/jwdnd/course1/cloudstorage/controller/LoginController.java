package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.udacity.jwdnd.course1.cloudstorage.model.Constants.LOGIN_PAGE;
import static com.udacity.jwdnd.course1.cloudstorage.model.Constants.SIGNUP_SUCCESS_ATTRIBUTE;

/*
 * Controller for login
 */

@Controller
@RequestMapping("/login")
public class LoginController {

    //standard login view
    @GetMapping()
    public String loginView() {
        return LOGIN_PAGE;
    }

    //redirect from signup
    @GetMapping("/signup")
    public String redirectFromSignup(@ModelAttribute(SIGNUP_SUCCESS_ATTRIBUTE) final Model model){
        //show success message: You successfully signed up
        model.addAttribute(SIGNUP_SUCCESS_ATTRIBUTE, model);
        return LOGIN_PAGE                ;
    }

}
