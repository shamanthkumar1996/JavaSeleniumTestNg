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
 * ProductDetailsPage class represents Amazon product details page
 * Contains page elements and methods for product details operations
 */
public class ProductDetailsPage {

    protected WebDriver driver;
    private static final Logger logger = LogManager.getLogger(ProductDetailsPage.class);

    // Locators
    @FindBy(xpath = "//h1//span[@class='a-size-large product-title']")
    private org.openqa.selenium.WebElement productTitle;

    @FindBy(xpath = "//span[@class='a-price-whole']")
    private org.openqa.selenium.WebElement productPrice;

    @FindBy(xpath = "//span[@data-a-icon-star]")
    private org.openqa.selenium.WebElement productRating;

    @FindBy(id = "add-to-cart-button")
    private org.openqa.selenium.WebElement addToCartButton;

    @FindBy(xpath = "//span[@class='a-size-medium a-color-base']")
    private org.openqa.selenium.WebElement productDescription;

    @FindBy(xpath = "//div[@id='dp-container']")
    private org.openqa.selenium.WebElement productDetailsContainer;

    // Alternative locators
    private static final By PRODUCT_TITLE = By.xpath("//h1//span[@class='a-size-large product-title']");
    private static final By PRODUCT_PRICE = By.xpath("//span[@class='a-price-whole']");
    private static final By ADD_TO_CART = By.id("add-to-cart-button");
    private static final By PRODUCT_DESCRIPTION = By.xpath("//span[@class='a-size-medium a-color-base']");
    private static final By PRODUCT_DETAILS_CONTAINER = By.xpath("//div[@id='dp-container']");

    /**
     * Constructor to initialize page elements
     *
     * @param driver WebDriver instance
     */
    public ProductDetailsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Check if product details page is displayed
     *
     * @return true if page is displayed
     */
    public boolean isProductDetailsPageDisplayed() {
        return WebUtils.isElementDisplayed(driver, PRODUCT_DETAILS_CONTAINER);
    }

    /**
     * Get product title
     *
     * @return Product title text
     */
    @Step("Get product title")
    public String getProductTitle() {
        String title = WebUtils.getText(driver, PRODUCT_TITLE);
        logger.info("Product title: " + title);
        return title;
    }

    /**
     * Get product price
     *
     * @return Product price text
     */
    @Step("Get product price")
    public String getProductPrice() {
        String price = WebUtils.getText(driver, PRODUCT_PRICE);
        logger.info("Product price: " + price);
        return price;
    }

    /**
     * Get product rating
     *
     * @return Product rating
     */
    @Step("Get product rating")
    public String getProductRating() {
        String rating = WebUtils.getAttribute(driver, By.xpath("//span[@data-a-icon-star]"), "aria-label");
        logger.info("Product rating: " + rating);
        return rating;
    }

    /**
     * Get product description
     *
     * @return Product description text
     */
    @Step("Get product description")
    public String getProductDescription() {
        String description = WebUtils.getText(driver, PRODUCT_DESCRIPTION);
        logger.info("Product description: " + description);
        return description;
    }

    /**
     * Add product to cart
     */
    @Step("Add product to cart")
    public void addToCart() {
        WebUtils.click(driver, ADD_TO_CART);
        logger.info("Product added to cart");
    }

    /**
     * Check if add to cart button is available
     *
     * @return true if button is available
     */
    public boolean isAddToCartButtonAvailable() {
        return WebUtils.isElementDisplayed(driver, ADD_TO_CART);
    }

    /**
     * Scroll to product details
     */
    @Step("Scroll to product details")
    public void scrollToProductDetails() {
        WebUtils.waitForPageLoad(driver);
        logger.info("Scrolled to product details");
    }
}
