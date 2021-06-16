package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CloudContent;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.udacity.jwdnd.course1.cloudstorage.controller.HomeController.successErrorMessage;
import static com.udacity.jwdnd.course1.cloudstorage.model.Constants.*;

/*
 * Controller for credentials
 */

@Controller
public class CredentialController {

    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    //create + edit credential
    @PostMapping("/credential")
    public String postCredential(RedirectAttributes redirectAttributes, Authentication authentication,
                                 @ModelAttribute(CLOUD_CONTENT_ATTRIBUTE) CloudContent cloudContent, Model model) {
        int userId = userService.getUser(authentication.getName()).getUserId();

        String successMessage;
        boolean isSuccess;

        //create
        if (cloudContent.getCredentialId().equals("")) {
            int credentialId = credentialService.addCredential(cloudContent.getUrl(),
                    cloudContent.getUsername(), cloudContent.getDecryptedPassword(), userId);
            cloudContent.setCredentialId(String.valueOf(credentialId));
            isSuccess = credentialId != 0;
            successMessage = CREATE_CREDENTIALS;
        }
        //edit
        else {
            isSuccess = credentialService.updateCredential(Integer.parseInt(cloudContent.getCredentialId()),
                    cloudContent.getUrl(), cloudContent.getUsername(), cloudContent.getDecryptedPassword(), userId);
            successMessage = EDIT_CREDENTIALS;
        }

        //update template
        model.addAttribute(CREDENTIALS_ATTRIBUTE, credentialService.getAllCredentials(userId));
        successErrorMessage(redirectAttributes, successMessage, isSuccess);
        return REDIRECT_CREDENTIAL_PAGE;
    }

    //delete credentials
    @GetMapping("/delete_credential/{id}")
    public String deleteCredential(@PathVariable int id, RedirectAttributes redirectAttributes,
                                   Authentication authentication) {
        boolean isSuccess;
        //delete only credential own by user bc href link is public
        int userId = userService.getUser(authentication.getName()).getUserId();
        //delete
        if (userId == credentialService.getCredentialByCredentialId(id).getUserId()) {
            isSuccess = credentialService.deleteCredential(id);
        } else {
            isSuccess = false;
        }
        successErrorMessage(redirectAttributes, DELETE_CREDENTIALS, isSuccess);
        return REDIRECT_CREDENTIAL_PAGE;
    }
}
