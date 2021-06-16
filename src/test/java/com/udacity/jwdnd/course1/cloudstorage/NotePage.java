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

public class NotePage {

    private final WebDriver driver;

    @FindBy(id = "logout-button")
    private WebElement buttonLogout;

    @FindBy(id = "nav-notes-tab")
    private WebElement navTabNotes;

    @FindBy(id = "new-note-button")
    private WebElement buttonNewNote;

    @FindBy(id = "note-title")
    private WebElement inputNoteTitle;

    @FindBy(id = "userTable")
    private WebElement userTable;

    @FindBy(id = "note-description")
    private WebElement inputNoteDescription;

    @FindBy(id = "note-submit-button")
    private WebElement buttonSubmitNote;

    @FindBy(id = "delete-note")
    private WebElement buttonDelete;


    public NotePage(WebDriver driver)  {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void logout(){
        waitForPageUntil(ExpectedConditions.visibilityOf(navTabNotes));
        buttonLogout.click();
    }
    public void navNotes() {
        waitForPageUntil(ExpectedConditions.visibilityOf(navTabNotes));
        navTabNotes.click();
        waitForPageUntil(ExpectedConditions.elementToBeClickable(buttonNewNote));
    }

    public void addNotes(String title, String description){
        navNotes();
        buttonNewNote.click();
        waitForPageUntil(ExpectedConditions.visibilityOf(inputNoteTitle));
        inputNoteTitle.sendKeys(title);
        inputNoteDescription.sendKeys(description);
        buttonSubmitNote.click();
        waitForPageEqual(ExpectedConditions.invisibilityOf(buttonSubmitNote));
    }

    public String getLastNoteTitle(){
        navNotes();
        List<WebElement> rows = userTable.findElements(By.tagName("tr"));
        String lastEntry = rows.get(rows.size() - 1).findElement(By.tagName("th")).getText();
        return  lastEntry;
    }

    public void editLastNote(String title){
        navNotes();
        List<WebElement> rows = userTable.findElements(By.tagName("tr"));
        rows.get(rows.size() - 1).findElement(By.id("note-edit-button")).click();
        waitForPageUntil(ExpectedConditions.visibilityOf(inputNoteTitle));
        inputNoteTitle.clear();
        inputNoteTitle.sendKeys(title);
        buttonSubmitNote.click();
        waitForPageEqual(ExpectedConditions.invisibilityOf(buttonSubmitNote));
    }

    public void deleteLastNote(){
        navNotes();
        List<WebElement> rows = userTable.findElements(By.tagName("tr"));
        rows.get(rows.size() - 1).findElement(By.linkText("Delete")).click();
    }

    private void waitForPageUntil(ExpectedCondition<WebElement> element) {
        new WebDriverWait(driver, 20).until(element);
    }

    private void waitForPageEqual(ExpectedCondition<Boolean> element){
        new WebDriverWait(driver, 20).equals(element);
    }


}
