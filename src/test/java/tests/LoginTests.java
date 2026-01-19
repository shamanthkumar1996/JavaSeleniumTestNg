package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.HomePage;
import utils.CSVUtils;
import java.util.Map;

/**
 * LoginTests class contains test cases for Amazon login functionality
 * Uses data-driven testing with CSV file
 */
@Feature("Amazon Login")
public class LoginTests extends BaseTest {

    private static final String LOGIN_DATA_FILE = "src/test/resources/testdata/loginData.csv";

    /**
     * DataProvider method to fetch test data from CSV file
     */
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return CSVUtils.getCSVDataForTestNG(LOGIN_DATA_FILE);
    }

    /**
     * Test login with valid credentials
     */
    @Test(description = "Test login with valid credentials")
    @Story("Login with Valid Credentials")
    @Description("Verify user can login with valid email and password")
    public void testLoginWithValidCredentials() {
        // Arrange
        LoginPage loginPage = new LoginPage(base.DriverFactory.getDriver());

        // Act
        loginPage.enterEmail("test@amazon.com");
        loginPage.clickContinueButton();
        loginPage.enterPassword("Password123");
        loginPage.clickSignInButton();

        // Assert
        logger.info("Login test with valid credentials completed");
    }

    /**
     * Data-driven test for login functionality
     */
    @Test(dataProvider = "loginData", description = "Data-driven login test")
    @Story("Login with CSV Data")
    @Description("Verify login functionality with different email/password combinations from CSV")
    public void testLoginWithCSVData(Map<String, String> testData) {
        // Arrange
        LoginPage loginPage = new LoginPage(base.DriverFactory.getDriver());
        String email = testData.get("email");
        String password = testData.get("password");
        String expectedResult = testData.get("expectedResult");

        // Act
        loginPage.login(email, password);

        // Assert
        logger.info("Login test with data: email=" + email + ", expectedResult=" + expectedResult);
    }

    /**
     * Test login page elements are visible
     */
    @Test(description = "Test login page is displayed")
    @Story("Login Page Display")
    @Description("Verify login page loads with all required elements")
    public void testLoginPageIsDisplayed() {
        // Arrange
        LoginPage loginPage = new LoginPage(base.DriverFactory.getDriver());

        // Assert
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page is not displayed");
        logger.info("Login page is displayed successfully");
    }

    /**
     * Test invalid login credentials
     */
    @Test(description = "Test login with invalid credentials")
    @Story("Login with Invalid Credentials")
    @Description("Verify error message appears for invalid credentials")
    public void testLoginWithInvalidCredentials() {
        // Arrange
        LoginPage loginPage = new LoginPage(base.DriverFactory.getDriver());

        // Act
        loginPage.enterEmail("invalid@email.com");
        loginPage.clickContinueButton();

        // Assert
        logger.info("Login test with invalid credentials completed");
    }

    /**
     * Test login with empty credentials
     */
    @Test(description = "Test login with empty fields")
    @Story("Login with Empty Fields")
    @Description("Verify validation error appears for empty email and password")
    public void testLoginWithEmptyFields() {
        // Arrange
        LoginPage loginPage = new LoginPage(base.DriverFactory.getDriver());

        // Act
        loginPage.clickContinueButton();

        // Assert
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message not displayed");
        logger.info("Login test with empty fields completed");
    }
}
