package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.WebUtils;
import io.qameta.allure.Step;
import java.util.List;

/**
 * HomePage class represents Amazon home page
 * Contains page elements and methods for home page operations
 */
public class HomePage {

    protected WebDriver driver;
    private static final Logger logger = LogManager.getLogger(HomePage.class);

    // Locators
    @FindBy(id = "nav-link-accountList-nav-line-1")
    private org.openqa.selenium.WebElement accountMenu;

    @FindBy(id = "twotabsearchtextbox")
    private org.openqa.selenium.WebElement searchBox;

    @FindBy(xpath = "//input[@value='Go']")
    private org.openqa.selenium.WebElement searchButton;

    @FindBy(xpath = "//span[@class='a-price-whole']")
    private List<org.openqa.selenium.WebElement> prices;

    @FindBy(xpath = "//img[@alt='Amazon.in']")
    private org.openqa.selenium.WebElement amazonLogo;

    // Alternative locators
    private static final By ACCOUNT_MENU = By.id("nav-link-accountList-nav-line-1");
    private static final By SEARCH_BOX = By.id("twotabsearchtextbox");
    private static final By SEARCH_BUTTON = By.xpath("//input[@value='Go']");
    private static final By AMAZON_LOGO = By.xpath("//img[@alt='Amazon.in']");

    /**
     * Constructor to initialize page elements
     *
     * @param driver WebDriver instance
     */
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Check if home page is displayed
     *
     * @return true if home page is displayed
     */
    public boolean isHomePageDisplayed() {
        return WebUtils.isElementDisplayed(driver, AMAZON_LOGO);
    }

    /**
     * Search for a product
     *
     * @param searchTerm Product to search for
     */
    @Step("Search for product: {searchTerm}")
    public void searchProduct(String searchTerm) {
        WebUtils.typeText(driver, SEARCH_BOX, searchTerm);
        WebUtils.click(driver, SEARCH_BUTTON);
        logger.info("Searched for: " + searchTerm);
    }

    /**
     * Click on account menu
     */
    @Step("Click on account menu")
    public void clickAccountMenu() {
        WebUtils.click(driver, ACCOUNT_MENU);
        logger.info("Account menu clicked");
    }

    /**
     * Get number of products displayed
     *
     * @return Number of products
     */
    public int getProductCount() {
        return prices.size();
    }

    /**
     * Verify search box is visible
     *
     * @return true if search box is visible
     */
    public boolean isSearchBoxVisible() {
        return WebUtils.isElementDisplayed(driver, SEARCH_BOX);
    }
}
