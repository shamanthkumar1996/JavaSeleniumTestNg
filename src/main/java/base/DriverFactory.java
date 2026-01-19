package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;

/**
 * DriverFactory class handles WebDriver initialization and management
 * Implements ThreadLocal to handle parallel test execution
 */
public class DriverFactory {

    private static final Logger logger = LogManager.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Duration IMPLICIT_WAIT = Duration.ofSeconds(10);
    private static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(20);

    /**
     * Initialize WebDriver based on browser type
     *
     * @param browser Browser name (chrome, firefox, edge)
     */
    public static void initDriver(String browser) {
        try {
            WebDriver webDriver = null;

            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized");
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.addArguments("--disable-popup-blocking");
                    webDriver = new ChromeDriver(chromeOptions);
                    logger.info("Chrome browser initialized");
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--start-maximized");
                    webDriver = new FirefoxDriver(firefoxOptions);
                    logger.info("Firefox browser initialized");
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--start-maximized");
                    webDriver = new EdgeDriver(edgeOptions);
                    logger.info("Edge browser initialized");
                    break;

                default:
                    logger.error("Browser: " + browser + " is not recognized");
                    throw new IllegalArgumentException("Browser not supported: " + browser);
            }

            driver.set(webDriver);
            webDriver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT);
            webDriver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT);

        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver for browser: " + browser, e);
            throw new RuntimeException("Failed to initialize WebDriver", e);
        }
    }

    /**
     * Get WebDriver instance
     *
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver == null) {
            logger.error("WebDriver is not initialized");
            throw new RuntimeException("WebDriver is not initialized. Call initDriver() first.");
        }
        return webDriver;
    }

    /**
     * Quit WebDriver and remove from ThreadLocal
     */
    public static void quitDriver() {
        try {
            WebDriver webDriver = driver.get();
            if (webDriver != null) {
                webDriver.quit();
                logger.info("WebDriver closed successfully");
            }
        } catch (Exception e) {
            logger.error("Error while closing WebDriver", e);
        } finally {
            driver.remove();
        }
    }

    /**
     * Check if WebDriver is initialized
     *
     * @return true if initialized, false otherwise
     */
    public static boolean isDriverInitialized() {
        return driver.get() != null;
    }
}