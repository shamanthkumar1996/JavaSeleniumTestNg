package utils;

import io.qameta.allure.Step;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;

/**
 * WebUtils class provides common WebDriver utility methods
 * Used for element interactions and waits
 */
public class WebUtils {

    private static final Logger logger = LogManager.getLogger(WebUtils.class);

    /**
     * Wait for element to be visible
     *
     * @param driver WebDriver instance
     * @param locator Element locator
     * @param timeoutInSeconds Timeout in seconds
     * @return WebElement if found
     */
    @Step("Wait for element to be visible: {locator}")
    public static WebElement waitForElement(WebDriver driver, By locator, int timeoutInSeconds) {
        try {
            WebElement element = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            logger.info("Element found and visible: " + locator);
            return element;
        } catch (TimeoutException e) {
            logger.error("Element not found or not visible: " + locator, e);
            throw new TimeoutException("Element not found: " + locator, e);
        }
    }

    /**
     * Wait for element to be clickable
     *
     * @param driver WebDriver instance
     * @param locator Element locator
     * @param timeoutInSeconds Timeout in seconds
     * @return WebElement if found
     */
    @Step("Wait for element to be clickable: {locator}")
    public static WebElement waitForElementToBeClickable(WebDriver driver, By locator, int timeoutInSeconds) {
        try {
            WebElement element = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                    .until(ExpectedConditions.elementToBeClickable(locator));
            logger.info("Element is clickable: " + locator);
            return element;
        } catch (TimeoutException e) {
            logger.error("Element not clickable: " + locator, e);
            throw new TimeoutException("Element not clickable: " + locator, e);
        }
    }

    /**
     * Click on an element
     *
     * @param driver WebDriver instance
     * @param locator Element locator
     */
    @Step("Click on element: {locator}")
    public static void click(WebDriver driver, By locator) {
        try {
            WebElement element = waitForElementToBeClickable(driver, locator, 10);
            element.click();
            logger.info("Clicked on element: " + locator);
        } catch (Exception e) {
            logger.error("Failed to click on element: " + locator, e);
            throw new RuntimeException("Failed to click on element: " + locator, e);
        }
    }

    /**
     * Type text in an input field
     *
     * @param driver WebDriver instance
     * @param locator Element locator
     * @param text Text to type
     */
    @Step("Type text '{text}' in element: {locator}")
    public static void typeText(WebDriver driver, By locator, String text) {
        try {
            WebElement element = waitForElement(driver, locator, 10);
            element.clear();
            element.sendKeys(text);
            logger.info("Typed text '" + text + "' in element: " + locator);
        } catch (Exception e) {
            logger.error("Failed to type text in element: " + locator, e);
            throw new RuntimeException("Failed to type text: " + locator, e);
        }
    }

    /**
     * Get text from an element
     *
     * @param driver WebDriver instance
     * @param locator Element locator
     * @return Text from the element
     */
    @Step("Get text from element: {locator}")
    public static String getText(WebDriver driver, By locator) {
        try {
            WebElement element = waitForElement(driver, locator, 10);
            String text = element.getText();
            logger.info("Retrieved text from element: " + text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element: " + locator, e);
            throw new RuntimeException("Failed to get text: " + locator, e);
        }
    }

    /**
     * Check if element is displayed
     *
     * @param driver WebDriver instance
     * @param locator Element locator
     * @return true if element is displayed, false otherwise
     */
    public static boolean isElementDisplayed(WebDriver driver, By locator) {
        try {
            return waitForElement(driver, locator, 5).isDisplayed();
        } catch (TimeoutException e) {
            logger.warn("Element not displayed: " + locator);
            return false;
        }
    }

    /**
     * Check if element exists on page
     *
     * @param driver WebDriver instance
     * @param locator Element locator
     * @return true if element exists, false otherwise
     */
    public static boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.findElement(locator);
            logger.info("Element present: " + locator);
            return true;
        } catch (NoSuchElementException e) {
            logger.warn("Element not present: " + locator);
            return false;
        }
    }

    /**
     * Navigate to URL
     *
     * @param driver WebDriver instance
     * @param url URL to navigate to
     */
    @Step("Navigate to URL: {url}")
    public static void navigateToUrl(WebDriver driver, String url) {
        try {
            driver.navigate().to(url);
            logger.info("Navigated to URL: " + url);
        } catch (Exception e) {
            logger.error("Failed to navigate to URL: " + url, e);
            throw new RuntimeException("Failed to navigate to URL: " + url, e);
        }
    }

    /**
     * Scroll to element
     *
     * @param driver WebDriver instance
     * @param element WebElement to scroll to
     */
    public static void scrollToElement(WebDriver driver, WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            logger.info("Scrolled to element");
        } catch (Exception e) {
            logger.error("Failed to scroll to element", e);
        }
    }

    /**
     * Execute JavaScript
     *
     * @param driver WebDriver instance
     * @param script JavaScript code
     * @return Result of script execution
     */
    public static Object executeScript(WebDriver driver, String script) {
        try {
            return ((JavascriptExecutor) driver).executeScript(script);
        } catch (Exception e) {
            logger.error("Failed to execute JavaScript: " + script, e);
            throw new RuntimeException("Failed to execute JavaScript", e);
        }
    }

    /**
     * Wait for page to load
     *
     * @param driver WebDriver instance
     */
    @Step("Wait for page to load")
    public static void waitForPageLoad(WebDriver driver) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(20))
                    .until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
            logger.info("Page loaded successfully");
        } catch (TimeoutException e) {
            logger.warn("Page load timeout", e);
        }
    }

    /**
     * Get attribute value from element
     *
     * @param driver WebDriver instance
     * @param locator Element locator
     * @param attributeName Attribute name
     * @return Attribute value
     */
    public static String getAttribute(WebDriver driver, By locator, String attributeName) {
        try {
            WebElement element = waitForElement(driver, locator, 10);
            return element.getAttribute(attributeName);
        } catch (Exception e) {
            logger.error("Failed to get attribute '" + attributeName + "' from element: " + locator, e);
            throw new RuntimeException("Failed to get attribute", e);
        }
    }
}
