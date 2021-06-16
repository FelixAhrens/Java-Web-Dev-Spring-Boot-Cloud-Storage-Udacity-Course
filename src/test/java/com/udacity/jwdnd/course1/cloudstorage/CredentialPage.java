package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CredentialPage {

    private final WebDriver driver;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navTabCredentials;

    @FindBy(id = "new-credential-button")
    private WebElement buttonNewCredential;

    @FindBy(id = "credential-url")
    private WebElement inputCredentialUrl;

    @FindBy(id = "credential-username")
    private WebElement inputCredentialUsername;

    @FindBy(id = "credential-password")
    private WebElement inputCredentialDecryptedPassword;

    @FindBy(id = "credential-password-table")
    private WebElement credentialEncryptedPassword;

    @FindBy(id = "credentialTable")
    private WebElement credentialTable;

    @FindBy(id = "credential-submit-button")
    private WebElement buttonSubmitCredential;

    @FindBy(id = "delete-credential")
    private WebElement buttonDelete;

    public CredentialPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void navCredentials(){
        waitForPageUntil(ExpectedConditions.visibilityOf(navTabCredentials));
        navTabCredentials.click();
        waitForPageUntil(ExpectedConditions.elementToBeClickable(buttonNewCredential));
    }

    public void addCredential(String url, String username, String password){
        navCredentials();
        buttonNewCredential.click();
        waitForPageUntil(ExpectedConditions.visibilityOf(inputCredentialUrl));
        inputCredentialUrl.sendKeys(url);
        inputCredentialUsername.sendKeys(username);
        inputCredentialDecryptedPassword.sendKeys(password);
        buttonSubmitCredential.click();
        waitForPageEqual(ExpectedConditions.invisibilityOf(buttonSubmitCredential));
    }

    public String[] getLastCredentialUrlEncryptedPassword(){
        navCredentials();
        List<WebElement> rows = credentialTable.findElements(By.tagName("tr"));
        if (rows.size() > 1) {
            List<WebElement> cols = rows.get(rows.size() - 1).findElements(By.tagName("td"));
            String lastUrl = rows.get(rows.size() - 1).findElement(By.tagName("th")).getText();
            String lastEncryptedPassword = cols.get(cols.size() - 1).getText();
            return new String[]{lastUrl,lastEncryptedPassword};
        }
        return new String[]{"",""};
    }

    public String editLastCredentialUrl(String url){
        navCredentials();
        List<WebElement> rows = credentialTable.findElements(By.tagName("tr"));
        rows.get(rows.size() - 1).findElement(By.id("credential-edit-button")).click();
        waitForPageUntil(ExpectedConditions.visibilityOf(inputCredentialUrl));
        inputCredentialUrl.clear();
        inputCredentialUrl.sendKeys(url);
        String decryptedPassword = inputCredentialDecryptedPassword.getAttribute("value");
        buttonSubmitCredential.click();
        waitForPageEqual(ExpectedConditions.invisibilityOf(buttonSubmitCredential));
        return decryptedPassword;
    }

    public void deleteLastCredential(){
        navCredentials();
        List<WebElement> rows = credentialTable.findElements(By.tagName("tr"));
        rows.get(rows.size() - 1).findElement(By.linkText("Delete")).click();
    }


    private void waitForPageUntil(ExpectedCondition<WebElement> element) {
        new WebDriverWait(driver, 20).until(element);
    }
    private void waitForPageEqual(ExpectedCondition<Boolean> element){
        new WebDriverWait(driver, 20).equals(element);
    }


}
