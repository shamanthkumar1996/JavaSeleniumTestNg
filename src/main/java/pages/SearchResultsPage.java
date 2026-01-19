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
 * SearchResultsPage class represents Amazon search results page
 * Contains page elements and methods for search results operations
 */
public class SearchResultsPage {

    protected WebDriver driver;
    private static final Logger logger = LogManager.getLogger(SearchResultsPage.class);

    // Locators
    @FindBy(xpath = "//span[@data-component-type='s-search-results']//span[@role='img'][@aria-label*='stars']")
    private List<org.openqa.selenium.WebElement> productRatings;

    @FindBy(xpath = "//span[@class='a-price-whole']")
    private List<org.openqa.selenium.WebElement> productPrices;

    @FindBy(xpath = "//h2//a[@class='a-link-normal s-link']")
    private List<org.openqa.selenium.WebElement> productTitles;

    @FindBy(xpath = "//span[@class='a-declarative']//span[@class='a-icon-star-small']//span[@class='a-icon-star']")
    private List<org.openqa.selenium.WebElement> ratings;

    @FindBy(xpath = "//span[@aria-current='page']")
    private org.openqa.selenium.WebElement currentPage;

    @FindBy(xpath = "//a[@rel='next']")
    private org.openqa.selenium.WebElement nextPageButton;

    @FindBy(xpath = "//span[@class='a-size-base']")
    private org.openqa.selenium.WebElement searchSummary;

    // Alternative locators
    private static final By PRODUCT_TITLES = By.xpath("//h2//a[@class='a-link-normal s-link']");
    private static final By NEXT_PAGE_BUTTON = By.xpath("//a[@rel='next']");
    private static final By SEARCH_SUMMARY = By.xpath("//span[@class='a-size-base']");

    /**
     * Constructor to initialize page elements
     *
     * @param driver WebDriver instance
     */
    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Get total number of products in search results
     *
     * @return Number of products
     */
    public int getProductCount() {
        return productTitles.size();
    }

    /**
     * Get product title by index
     *
     * @param index Product index
     * @return Product title
     */
    @Step("Get product title at index: {index}")
    public String getProductTitle(int index) {
        if (index < productTitles.size()) {
            return productTitles.get(index).getText();
        }
        logger.warn("Product index out of bounds: " + index);
        return null;
    }

    /**
     * Get product price by index
     *
     * @param index Product index
     * @return Product price
     */
    @Step("Get product price at index: {index}")
    public String getProductPrice(int index) {
        if (index < productPrices.size()) {
            return productPrices.get(index).getText();
        }
        logger.warn("Product price index out of bounds: " + index);
        return null;
    }

    /**
     * Click on product by index
     *
     * @param index Product index
     */
    @Step("Click on product at index: {index}")
    public void clickProductByIndex(int index) {
        if (index < productTitles.size()) {
            WebUtils.scrollToElement(driver, productTitles.get(index));
            productTitles.get(index).click();
            logger.info("Clicked on product at index: " + index);
        } else {
            logger.error("Product index out of bounds: " + index);
        }
    }

    /**
     * Check if next page button is available
     *
     * @return true if next page button is available
     */
    public boolean isNextPageAvailable() {
        return WebUtils.isElementDisplayed(driver, NEXT_PAGE_BUTTON);
    }

    /**
     * Click next page button
     */
    @Step("Click next page button")
    public void clickNextPage() {
        if (isNextPageAvailable()) {
            WebUtils.click(driver, NEXT_PAGE_BUTTON);
            logger.info("Navigated to next page");
        } else {
            logger.warn("Next page button is not available");
        }
    }

    /**
     * Get search summary text
     *
     * @return Search summary
     */
    public String getSearchSummary() {
        return WebUtils.getText(driver, SEARCH_SUMMARY);
    }

    /**
     * Get current page number
     *
     * @return Current page number
     */
    public String getCurrentPageNumber() {
        return currentPage.getText();
    }

    /**
     * Verify search results are displayed
     *
     * @return true if results are displayed
     */
    public boolean areSearchResultsDisplayed() {
        return getProductCount() > 0;
    }

    /**
     * Click on product by title
     *
     * @param productTitle Title of product to click
     */
    @Step("Click on product with title: {productTitle}")
    public void clickProductByTitle(String productTitle) {
        for (org.openqa.selenium.WebElement product : productTitles) {
            if (product.getText().contains(productTitle)) {
                WebUtils.scrollToElement(driver, product);
                product.click();
                logger.info("Clicked on product: " + productTitle);
                return;
            }
        }
        logger.error("Product not found: " + productTitle);
    }
}
