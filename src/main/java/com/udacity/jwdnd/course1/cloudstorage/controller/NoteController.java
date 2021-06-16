package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CloudContent;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
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
 * Controller for notes
 */

@Controller
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    //create + edit note
    @PostMapping("/note")
    public String postNote(RedirectAttributes redirectAttributes, Authentication authentication,
                           @ModelAttribute(CLOUD_CONTENT_ATTRIBUTE) CloudContent cloudContent, Model model){
        int userId = userService.getUser(authentication.getName()).getUserId();

        String successMessage;
        boolean isSuccess;
        //create note
        if (cloudContent.getNoteId().equals("")){
            int noteId = noteService.addNote(cloudContent.getNoteTitle(),
                    cloudContent.getNoteDescription(), userId);
            cloudContent.setNoteId(String.valueOf(noteId));
            isSuccess = noteId != 0;
            successMessage = CREATE_NOTES;
        }
        //Edit note
        else {
            isSuccess = noteService.updateNote(Integer.parseInt(cloudContent.getNoteId()),
                    cloudContent.getNoteTitle(), cloudContent.getNoteDescription(), userId);
            successMessage = EDIT_NOTES;
        }
        //update template
        model.addAttribute(NOTES_ATTRIBUTE, noteService.getAllNotes(userId));
        successErrorMessage(redirectAttributes, successMessage, isSuccess);
        return REDIRECT_NOTE_PAGE;
    }

    //delete note
    @GetMapping("/delete_note/{id}")
    public String deleteNote(@PathVariable int id, RedirectAttributes redirectAttributes,
                             Authentication authentication){
        boolean isSuccess;
        //delete only notes own by user bc href link is public
        int userId = userService.getUser(authentication.getName()).getUserId();
        //delete note
        if (userId == noteService.getNoteByNoteId(id).getUserId()) {
            isSuccess = noteService.deleteNote(id);
        } else {
            isSuccess = false;
        }
        successErrorMessage(redirectAttributes, DELETE_NOTES, isSuccess);
        return REDIRECT_NOTE_PAGE;
    }

}
