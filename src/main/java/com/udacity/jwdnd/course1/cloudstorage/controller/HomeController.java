package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CloudContent;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.udacity.jwdnd.course1.cloudstorage.model.Constants.*;

/*
* Controller for main/home request
 */

@Controller
public class HomeController {

    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final UserService userService;

    public HomeController(FileService fileService, NoteService noteService,CredentialService credentialService, UserService userService) {
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.userService = userService;
    }

    //set all user data
    @GetMapping("/home")
    public String getHomePage(Authentication authentication, CloudContent cloudContent, Model model){
        int userId = userService.getUser(authentication.getName()).getUserId();
        model.addAttribute(FILES_ATTRIBUTE, fileService.getAllFiles(userId));
        model.addAttribute(NOTES_ATTRIBUTE, this.noteService.getAllNotes(userId));
        model.addAttribute(CREDENTIALS_ATTRIBUTE, this.credentialService.getAllCredentials(userId));
        return HOME_PAGE;
    }

    //Handle global errors
    @RequestMapping("/")
    public void handleRequest() {
        throw new RuntimeException("");
    }

    //Success + error message for user input
    public static void successErrorMessage(RedirectAttributes redirectAttributes, String message, boolean success){
        if (success) {
            redirectAttributes.addFlashAttribute(SUCCESS_ATTRIBUTE, message);
        } else {
            redirectAttributes.addFlashAttribute(ERROR_ATTRIBUTE, message);
        }
    }

}
