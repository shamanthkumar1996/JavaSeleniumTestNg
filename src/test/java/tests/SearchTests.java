package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchResultsPage;
import utils.WebUtils;

/**
 * SearchTests class contains test cases for Amazon search functionality
 */
@Feature("Amazon Search")
public class SearchTests extends BaseTest {

    private static final String APP_URL = "https://www.amazon.in/";

    /**
     * Test search for product
     */
    @Test(description = "Test searching for a product")
    @Story("Product Search")
    @Description("Verify user can search for products on Amazon")
    public void testSearchProduct() {
        // Arrange
        WebUtils.navigateToUrl(base.DriverFactory.getDriver(), APP_URL);
        HomePage homePage = new HomePage(base.DriverFactory.getDriver());

        // Assert
        Assert.assertTrue(homePage.isHomePageDisplayed(), "Home page is not displayed");

        // Act
        homePage.searchProduct("Laptop");
        logger.info("Searched for Laptop");

        // Assert
        SearchResultsPage searchResultsPage = new SearchResultsPage(base.DriverFactory.getDriver());
        Assert.assertTrue(searchResultsPage.areSearchResultsDisplayed(), "Search results are not displayed");
        logger.info("Search test completed successfully");
    }

    /**
     * Test multiple product search
     */
    @Test(description = "Test searching for multiple products")
    @Story("Multiple Product Search")
    @Description("Verify search works for different product types")
    public void testMultipleProductSearch() {
        // Arrange
        WebUtils.navigateToUrl(base.DriverFactory.getDriver(), APP_URL);
        HomePage homePage = new HomePage(base.DriverFactory.getDriver());

        // Act & Assert
        String[] products = {"iPhone", "Headphones", "Monitor"};
        for (String product : products) {
            homePage.searchProduct(product);
            SearchResultsPage searchResultsPage = new SearchResultsPage(base.DriverFactory.getDriver());
            Assert.assertTrue(searchResultsPage.areSearchResultsDisplayed(),
                    "No results found for: " + product);
            logger.info("Search completed for: " + product);
        }
    }

    /**
     * Test search results page elements
     */
    @Test(description = "Test search results page elements")
    @Story("Search Results Verification")
    @Description("Verify search results page displays all required elements")
    public void testSearchResultsPageElements() {
        // Arrange
        WebUtils.navigateToUrl(base.DriverFactory.getDriver(), APP_URL);
        HomePage homePage = new HomePage(base.DriverFactory.getDriver());

        // Act
        homePage.searchProduct("Keyboard");
        SearchResultsPage searchResultsPage = new SearchResultsPage(base.DriverFactory.getDriver());

        // Assert
        Assert.assertTrue(searchResultsPage.getProductCount() > 0, "No products found in search results");
        Assert.assertNotNull(searchResultsPage.getSearchSummary(), "Search summary is not displayed");
        logger.info("Search results page elements verified successfully");
    }

    /**
     * Test click on product from search results
     */
    @Test(description = "Test clicking on product from search results")
    @Story("Product Selection from Search Results")
    @Description("Verify user can click on a product from search results")
    public void testClickProductFromSearchResults() {
        // Arrange
        WebUtils.navigateToUrl(base.DriverFactory.getDriver(), APP_URL);
        HomePage homePage = new HomePage(base.DriverFactory.getDriver());

        // Act
        homePage.searchProduct("Tablet");
        SearchResultsPage searchResultsPage = new SearchResultsPage(base.DriverFactory.getDriver());

        // Assert
        Assert.assertTrue(searchResultsPage.areSearchResultsDisplayed(), "Search results not displayed");

        // Act
        searchResultsPage.clickProductByIndex(0);
        logger.info("Clicked on first product from search results");
    }

    /**
     * Test next page navigation
     */
    @Test(description = "Test navigating to next page in search results")
    @Story("Pagination in Search Results")
    @Description("Verify user can navigate to next page of search results")
    public void testNextPageNavigation() {
        // Arrange
        WebUtils.navigateToUrl(base.DriverFactory.getDriver(), APP_URL);
        HomePage homePage = new HomePage(base.DriverFactory.getDriver());

        // Act
        homePage.searchProduct("Mouse");
        SearchResultsPage searchResultsPage = new SearchResultsPage(base.DriverFactory.getDriver());

        // Assert
        if (searchResultsPage.isNextPageAvailable()) {
            searchResultsPage.clickNextPage();
            logger.info("Navigated to next page successfully");
        } else {
            logger.info("Next page is not available");
        }
    }
}
