package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.WebUtils;
import io.qameta.allure.Step;

/**
 * LoginPage class represents Amazon login page
 * Contains page elements and methods for login operations
 */
public class LoginPage {

    protected WebDriver driver;
    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    // Locators using @FindBy annotation
    @FindBy(id = "ap_email")
    private org.openqa.selenium.WebElement emailField;

    @FindBy(id = "continue")
    private org.openqa.selenium.WebElement continueButton;

    @FindBy(id = "ap_password")
    private org.openqa.selenium.WebElement passwordField;

    @FindBy(id = "signInSubmit")
    private org.openqa.selenium.WebElement signInButton;

    @FindBy(xpath = "//h1[@class='a-spacing-small']")
    private org.openqa.selenium.WebElement loginHeading;

    @FindBy(xpath = "//div[@class='a-alert-content']")
    private org.openqa.selenium.WebElement errorMessage;

    // Alternative locators
    private static final By EMAIL_FIELD = By.id("ap_email");
    private static final By CONTINUE_BUTTON = By.id("continue");
    private static final By PASSWORD_FIELD = By.id("ap_password");
    private static final By SIGN_IN_BUTTON = By.id("signInSubmit");
    private static final By LOGIN_HEADING = By.xpath("//h1[@class='a-spacing-small']");
    private static final By ERROR_MESSAGE = By.xpath("//div[@class='a-alert-content']");

    /**
     * Constructor to initialize page elements
     *
     * @param driver WebDriver instance
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Enter email address
     *
     * @param email Email address
     */
    @Step("Enter email: {email}")
    public void enterEmail(String email) {
        WebUtils.typeText(driver, EMAIL_FIELD, email);
        logger.info("Email entered: " + email);
    }

    /**
     * Click continue button
     */
    @Step("Click continue button")
    public void clickContinueButton() {
        WebUtils.click(driver, CONTINUE_BUTTON);
        logger.info("Continue button clicked");
    }

    /**
     * Enter password
     *
     * @param password Password
     */
    @Step("Enter password")
    public void enterPassword(String password) {
        WebUtils.typeText(driver, PASSWORD_FIELD, password);
        logger.info("Password entered");
    }

    /**
     * Click sign in button
     */
    @Step("Click sign in button")
    public void clickSignInButton() {
        WebUtils.click(driver, SIGN_IN_BUTTON);
        logger.info("Sign in button clicked");
    }

    /**
     * Perform complete login with email and password
     *
     * @param email Email address
     * @param password Password
     */
    @Step("Login with email: {email}")
    public void login(String email, String password) {
        enterEmail(email);
        clickContinueButton();
        enterPassword(password);
        clickSignInButton();
        logger.info("Login attempted with email: " + email);
    }

    /**
     * Get login page heading text
     *
     * @return Heading text
     */
    public String getLoginHeadingText() {
        return WebUtils.getText(driver, LOGIN_HEADING);
    }

    /**
     * Get error message text
     *
     * @return Error message
     */
    public String getErrorMessage() {
        return WebUtils.getText(driver, ERROR_MESSAGE);
    }

    /**
     * Check if login page is displayed
     *
     * @return true if login page is displayed
     */
    public boolean isLoginPageDisplayed() {
        return WebUtils.isElementDisplayed(driver, LOGIN_HEADING);
    }

    /**
     * Check if error message is displayed
     *
     * @return true if error message is displayed
     */
    public boolean isErrorMessageDisplayed() {
        return WebUtils.isElementDisplayed(driver, ERROR_MESSAGE);
    }
}
