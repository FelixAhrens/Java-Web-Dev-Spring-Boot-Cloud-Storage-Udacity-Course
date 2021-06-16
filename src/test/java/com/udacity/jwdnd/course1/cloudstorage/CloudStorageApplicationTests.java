package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

/*
* Selenium tests for basic functions
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	private NotePage notePage;
	private CredentialPage credentialPage;
	private SignupPage signupPage;
	private LoginPage loginPage;

	private final String firstName = "Felix";
	private final String lastName = "Ahrens";
	private final String username = "admin";
	private final String password = "pass";

	private final String noteTitle = "Test: Note";
	private final String noteDescription = "Lorem ipsum dolor sit amet";

	private final String credentialUrl = "www.cloud-storage.com";
	private final String credentialUsername = "Felix";
	private final String credentialPassword = "pass";

	private static boolean isUserCreated;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		isUserCreated = false;
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		//signup only at first test and login
		if (!isUserCreated) {
			signUp();
			isUserCreated = true;
		}
		login();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	/*
	* 1. Write Tests for User Signup, Login, and Unauthorized Access Restrictions.
	* 1.1. Write a test that verifies that an unauthorized user can only access the login and signup pages.
	 */
	@Test
	public void testUnauthorizedUserAccess() {
		//logout bc login in @BeforeEach
		logout();
		//test pages and access
		goToLoginPage();
		Assertions.assertEquals("Login", driver.getTitle());
		goToSignupPage();
		Assertions.assertEquals("Sign Up", driver.getTitle());
		goToHomePage();
		Assertions.assertNotEquals("Home", driver.getTitle());
		Assertions.assertEquals("Login", driver.getTitle());
	}
	/*
	* 1.2. Write a test that signs up a new user, logs in, verifies that the home page is accessible,
	* 		logs out, and verifies that the home page is no longer accessible.
	 */
	@Test
	public void testSignupLoginLogout() {
		//signup and login in @BeforeEach
		//after login: verify home page is accessible
		notePage = new NotePage(driver);
		Assertions.assertEquals("Home", driver.getTitle());
		//logout and verify home page is no longer accessible
		notePage.logout();
		goToHomePage();
		Assertions.assertNotEquals("Home", driver.getTitle());
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/*
	2. Write Tests for Note Creation, Viewing, Editing, and Deletion.
	2.1. Write a test that creates a note, and verifies it is displayed.
	 */
	@Test
	public void createNote() {
		notePage = new NotePage(driver);
		notePage.addNotes(noteTitle, noteDescription);
		goToHomePage();
		Assertions.assertEquals(noteTitle, notePage.getLastNoteTitle());
	}

	/*
	2.2. Write a test that edits an existing note and verifies that the changes are displayed.
	 */
	@Test
	public void editNote() {
		String noteTitleEdit = "Edited note title";
		notePage = new NotePage(driver);
		notePage.addNotes(noteTitle, noteDescription);
		goToHomePage();
		notePage.editLastNote(noteTitleEdit);
		goToHomePage();
		Assertions.assertEquals(noteTitleEdit, notePage.getLastNoteTitle());
	}

	/*
	2.3. Write a test that deletes a note and verifies that the note is no longer displayed.
	 */
	@Test
	public void deleteNote() {
		String noteTitleDelete = noteTitle + "Delete";
		notePage = new NotePage(driver);
		notePage.addNotes(noteTitleDelete, noteDescription);
		goToHomePage();
		notePage.deleteLastNote();
		goToHomePage();
		Assertions.assertNotEquals(noteTitleDelete, notePage.getLastNoteTitle());
	}

	/*
	3. Write Tests for Credential Creation, Viewing, Editing, and Deletion.
	3.1. Write a test that creates a set of credentials, verifies that they are displayed,
			and verifies that the displayed password is encrypted.
	 */
	@Test
	public void createCredential() {
		credentialPage = new CredentialPage(driver);
		//creates a set of credentials
		credentialPage.addCredential(credentialUrl, credentialUsername, credentialPassword);
		goToHomePage();
		//verify that they are displayed
		Assertions.assertEquals(credentialUrl, credentialPage.getLastCredentialUrlEncryptedPassword()[0]);
		//verify that the displayed password is encrypted
		Assertions.assertNotEquals(credentialPassword, credentialPage.getLastCredentialUrlEncryptedPassword()[1]);
	}

	/*
	3.2. Write a test that views an existing set of credentials, verifies that the viewable
			password is unencrypted, edits the credentials, and verifies that the changes are displayed.
	 */
	@Test
	public void editCredential() {
		String credentialUrlEdit = "www.super-cloud-storage.com";
		credentialPage = new CredentialPage(driver);
		credentialPage.addCredential(credentialUrl, credentialUsername, credentialPassword);
		goToHomePage();
		//view an existing set of credentials and edit the credentials -> get decrypted password
		String decryptedPassword = credentialPage.editLastCredentialUrl(credentialUrlEdit);
		goToHomePage();
		//verify that the viewable password is unencrypted
		Assertions.assertEquals(credentialPassword, decryptedPassword);
		//verify that the changes are displayed
		Assertions.assertEquals(credentialUrlEdit, credentialPage.getLastCredentialUrlEncryptedPassword()[0]);
	}
	/*
	3.3. Write a test that deletes an existing set of credentials and verifies that the
			credentials are no longer displayed.
	 */
	@Test
	public void deleteCredential() {
		String credentialUrlDelete = "www.delete.com";
		credentialPage= new CredentialPage(driver);
		credentialPage.addCredential(credentialUrlDelete, credentialUsername, credentialPassword);
		goToHomePage();
		//delete an existing set of credentials
		credentialPage.deleteLastCredential();
		goToHomePage();
		//verify that the credentials are no longer displayed
		Assertions.assertNotEquals(credentialUrlDelete, credentialPage.getLastCredentialUrlEncryptedPassword()[0]);
	}

	public void goToSignupPage(){
		driver.get("http://localhost:" + this.port + "/signup");
	}

	public void goToLoginPage(){
		driver.get("http://localhost:" + port + "/login");
	}

	public void goToHomePage(){
		driver.get("http://localhost:" + port + "/home");
	}

	public void signUp(){
		goToSignupPage();
		signupPage = new SignupPage(driver);
		signupPage.enterData(firstName, lastName, username, password);
	}
	public void login(){
		goToLoginPage();
		loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		goToHomePage();
	}
	public void logout(){
		notePage = new NotePage(driver);
		notePage.logout();
	}

}
