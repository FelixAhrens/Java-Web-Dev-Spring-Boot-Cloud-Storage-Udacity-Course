package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstNameField;

    @FindBy(id = "inputLastName")
    private WebElement inputLastNameField;

    @FindBy(id = "inputUsername")
    private WebElement inputUsernameField;

    @FindBy(id = "inputPassword")
    private WebElement inputPasswordField;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void enterData(String firstName, String lastName, String username, String password){
        inputFirstNameField.sendKeys(firstName);
        inputLastNameField.sendKeys(lastName);
        inputUsernameField.sendKeys(username);
        inputPasswordField.sendKeys(password);
        submitButton.click();
    }
}
