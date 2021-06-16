package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CloudContent;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

import static com.udacity.jwdnd.course1.cloudstorage.controller.HomeController.successErrorMessage;
import static com.udacity.jwdnd.course1.cloudstorage.model.Constants.*;

/*
 * Controller for files
 */

@Controller
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    //download file
    @GetMapping("/download/{id}")
    @ResponseBody
    public ResponseEntity<Resource> fileDownload(@PathVariable int id, Authentication authentication,
                                                 RedirectAttributes redirectAttributes) throws Exception {
        int userId = userService.getUser(authentication.getName()).getUserId();
        File file = fileService.getFileByFileId(id);
        //only files own by user
        if (userId == file.getUserId()) {
            try {
                //download file
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(file.getContentType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                                file.getFilename() + "\"")
                        .body(new ByteArrayResource(file.getFileData()));

            } catch (Exception e) {
                throw new Exception(ERROR_DOWNLOAD_FILE);
            }
        }

        successErrorMessage(redirectAttributes, DOWNLOAD, false);
        //return no content if download fails
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //upload file
    @PostMapping("/upload")
    public String fileUpload(@RequestParam("fileUpload") MultipartFile fileUpload,
                             RedirectAttributes redirectAttributes, Authentication authentication,
                             @ModelAttribute(CLOUD_CONTENT_ATTRIBUTE) CloudContent cloudContent, Model model){
        int userId = userService.getUser(authentication.getName()).getUserId();

        String successMessage;
        boolean isSuccess;

        //file not empty
        fileUpload:
        if (!fileUpload.isEmpty()) {
            //file with same name
            for (File file : fileService.getAllFiles(userId)){
                if (fileUpload.getOriginalFilename().equals(file.getFilename())) {
                    successMessage = SAME_FILE;
                    isSuccess = false;
                    break fileUpload;
                }
            }
            //upload file
            isSuccess = postFile(userId, cloudContent, fileUpload);
            successMessage = UPLOAD_FILE;
        } else {
            //no file found
            isSuccess = false;
            successMessage = NOT_FOUND_FILE;
        }
        //update template
        model.addAttribute(FILES_ATTRIBUTE, this.fileService.getAllFiles(userId));
        successErrorMessage(redirectAttributes, successMessage, isSuccess);
        return REDIRECT_FILE_PAGE;
    }

    //upload logic
    public boolean postFile(int userId, CloudContent cloudContent, MultipartFile file) {
        try {
            //create file
            int fileId = fileService.addFile(file.getOriginalFilename(), file.getContentType(),
                    String.valueOf(file.getSize()), userId, file.getBytes());
            cloudContent.setFileId(String.valueOf(fileId));
            return fileId != 0;
        } catch (IOException e) {
            return false;
        }
    }

    //delete
    @GetMapping("/delete_file/{id}")
    public String deleteFile(@PathVariable int id, RedirectAttributes redirectAttributes,
                             Authentication authentication){
        boolean isSuccess;
        //delete only file own by user bc href link is public
        int userId = userService.getUser(authentication.getName()).getUserId();
        //delete
        if (userId == fileService.getFileByFileId(id).getUserId()) {
            isSuccess = fileService.deleteFile(id);
        } else {
            isSuccess = false;
        }
        successErrorMessage(redirectAttributes, DELETE_FILES, isSuccess);
        return REDIRECT_FILE_PAGE;
    }
}
