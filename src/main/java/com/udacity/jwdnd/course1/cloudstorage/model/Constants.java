package com.udacity.jwdnd.course1.cloudstorage.model;

public final class Constants {

    private Constants(){}
    //credentials
    public static final String CREATE_CREDENTIALS = "Credential creation";
    public static final String EDIT_CREDENTIALS = "Editing of the credential";
    public static final String DELETE_CREDENTIALS = "Deletion of credential";
    //notes
    public static final String CREATE_NOTES = "Note creation";
    public static final String EDIT_NOTES = "Editing of the note";
    public static final String DELETE_NOTES = "Deletion of note";
    //files
    public static final String ERROR_DOWNLOAD_FILE = "Error downloading file";
    public static final String DELETE_FILES = "Deletion of file";
    public static final String DOWNLOAD = "Download";
    public static final String SAME_FILE = "File with the same name already exists. File upload";
    public static final String UPLOAD_FILE = "File upload";
    public static final String NOT_FOUND_FILE = "No file found. File upload";
    //signup
    public static final String USER_ALREADY_EXITS = "The username already exists.";
    public static final String ERROR_SIGNUP = "There was an error signing you up. Please try again.";
    //pages
    public static final String REDIRECT_NOTE_PAGE = "redirect:home#nav-notes";
    public static final String REDIRECT_CREDENTIAL_PAGE = "redirect:home#nav-credentials";
    public static final String REDIRECT_FILE_PAGE = "redirect:/home#nav-files";
    public static final String REDIRECT_LOGIN = "redirect:/login";
    public static final String HOME_PAGE = "home";
    public static final String LOGIN_PAGE = "login";
    public static final String SIGNUP_PAGE = "signup";
    //attribute
    public static final String NOTES_ATTRIBUTE = "notes";
    public static final String FILES_ATTRIBUTE = "files";
    public static final String CREDENTIALS_ATTRIBUTE = "credentials";
    public static final String SIGNUP_SUCCESS_ATTRIBUTE = "signupSuccess";
    public static final String SIGNUP_ERROR_ATTRIBUTE = "signupError";
    public static final String CLOUD_CONTENT_ATTRIBUTE = "cloudContent";
    public static final String SUCCESS_ATTRIBUTE = "success";
    public static final String ERROR_ATTRIBUTE = "error";
}
