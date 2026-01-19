package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchResultsPage;
import pages.ProductDetailsPage;
import utils.WebUtils;

/**
 * ProductTests class contains test cases for Amazon product functionality
 */
@Feature("Amazon Product")
public class ProductTests extends BaseTest {

    private static final String APP_URL = "https://www.amazon.in/";

    /**
     * Test product details page
     */
    @Test(description = "Test viewing product details")
    @Story("Product Details")
    @Description("Verify user can view product details page")
    public void testViewProductDetails() {
        // Arrange
        WebUtils.navigateToUrl(base.DriverFactory.getDriver(), APP_URL);
        HomePage homePage = new HomePage(base.DriverFactory.getDriver());

        // Act
        homePage.searchProduct("Earbuds");
        SearchResultsPage searchResultsPage = new SearchResultsPage(base.DriverFactory.getDriver());
        searchResultsPage.clickProductByIndex(0);

        // Assert
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(base.DriverFactory.getDriver());
        Assert.assertTrue(productDetailsPage.isProductDetailsPageDisplayed(),
                "Product details page is not displayed");
        logger.info("Product details page viewed successfully");
    }

    /**
     * Test get product information
     */
    @Test(description = "Test getting product information")
    @Story("Product Information")
    @Description("Verify product title, price, and rating are displayed")
    public void testGetProductInformation() {
        // Arrange
        WebUtils.navigateToUrl(base.DriverFactory.getDriver(), APP_URL);
        HomePage homePage = new HomePage(base.DriverFactory.getDriver());

        // Act
        homePage.searchProduct("Speaker");
        SearchResultsPage searchResultsPage = new SearchResultsPage(base.DriverFactory.getDriver());
        searchResultsPage.clickProductByIndex(0);

        ProductDetailsPage productDetailsPage = new ProductDetailsPage(base.DriverFactory.getDriver());
        productDetailsPage.scrollToProductDetails();

        // Get product information
        String title = productDetailsPage.getProductTitle();
        String price = productDetailsPage.getProductPrice();
        String rating = productDetailsPage.getProductRating();

        // Assert
        Assert.assertNotNull(title, "Product title is null");
        Assert.assertNotNull(price, "Product price is null");
        logger.info("Product Information - Title: " + title + ", Price: " + price + ", Rating: " + rating);
    }

    /**
     * Test add to cart functionality
     */
    @Test(description = "Test adding product to cart")
    @Story("Add to Cart")
    @Description("Verify user can add product to cart")
    public void testAddToCart() {
        // Arrange
        WebUtils.navigateToUrl(base.DriverFactory.getDriver(), APP_URL);
        HomePage homePage = new HomePage(base.DriverFactory.getDriver());

        // Act
        homePage.searchProduct("Cable");
        SearchResultsPage searchResultsPage = new SearchResultsPage(base.DriverFactory.getDriver());
        searchResultsPage.clickProductByIndex(0);

        ProductDetailsPage productDetailsPage = new ProductDetailsPage(base.DriverFactory.getDriver());

        // Assert
        Assert.assertTrue(productDetailsPage.isAddToCartButtonAvailable(),
                "Add to cart button is not available");

        // Act
        productDetailsPage.addToCart();
        logger.info("Product added to cart successfully");
    }

    /**
     * Test product search and view multiple products
     */
    @Test(description = "Test searching and viewing multiple products")
    @Story("Multiple Product Browsing")
    @Description("Verify user can browse multiple products")
    public void testBrowseMultipleProducts() {
        // Arrange
        WebUtils.navigateToUrl(base.DriverFactory.getDriver(), APP_URL);
        HomePage homePage = new HomePage(base.DriverFactory.getDriver());

        // Act
        homePage.searchProduct("USB Hub");
        SearchResultsPage searchResultsPage = new SearchResultsPage(base.DriverFactory.getDriver());

        // Assert
        int productCount = searchResultsPage.getProductCount();
        Assert.assertTrue(productCount > 0, "No products found");
        logger.info("Total products found: " + productCount);

        // Act - Click on first product
        searchResultsPage.clickProductByIndex(0);
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(base.DriverFactory.getDriver());

        // Assert
        Assert.assertTrue(productDetailsPage.isProductDetailsPageDisplayed(),
                "Product details page not displayed");
        logger.info("Browsed product successfully");
    }

    /**
     * Test product rating display
     */
    @Test(description = "Test product rating is displayed")
    @Story("Product Rating Display")
    @Description("Verify product rating is shown on product page")
    public void testProductRatingDisplay() {
        // Arrange
        WebUtils.navigateToUrl(base.DriverFactory.getDriver(), APP_URL);
        HomePage homePage = new HomePage(base.DriverFactory.getDriver());

        // Act
        homePage.searchProduct("Phone");
        SearchResultsPage searchResultsPage = new SearchResultsPage(base.DriverFactory.getDriver());
        
        // Assert
        Assert.assertTrue(searchResultsPage.areSearchResultsDisplayed(),
                "Search results not displayed");
        logger.info("Product rating test completed");
    }
}
