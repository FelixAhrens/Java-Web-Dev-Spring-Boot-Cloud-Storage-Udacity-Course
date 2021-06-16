package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.udacity.jwdnd.course1.cloudstorage.model.Constants.*;

/*
 * Controller for signup
 */

@Controller()
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView() {
        return SIGNUP_PAGE;
    }

    @PostMapping()
    public String signupUser(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
        String signupError = null;

        //username are unique
        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupError = USER_ALREADY_EXITS;
        }

        //create user + handle error
        if (signupError == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0) {
                signupError = ERROR_SIGNUP;
            }
        }

        //Success + error message
        if (signupError == null) {
            model.addAttribute(SIGNUP_SUCCESS_ATTRIBUTE, true);
            redirectAttributes.addFlashAttribute(SIGNUP_SUCCESS_ATTRIBUTE, model);
            return REDIRECT_LOGIN;
        } else {
            model.addAttribute(SIGNUP_ERROR_ATTRIBUTE, signupError);
            return SIGNUP_PAGE;
        }
    }

}
