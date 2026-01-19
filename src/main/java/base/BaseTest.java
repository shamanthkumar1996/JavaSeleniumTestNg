package base;

import io.qameta.allure.Step;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * BaseTest class provides common setup and teardown for all test classes
 * All test classes should extend this class
 */
public class BaseTest {

    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    /**
     * Setup method - executed before each test
     * Initializes WebDriver based on browser parameter
     *
     * @param browser Browser name from testng.xml
     */
    @BeforeMethod
    @Parameters({"browser"})
    public void setup(String browser) {
        logger.info("Starting test setup with browser: " + browser);
        DriverFactory.initDriver(browser);
        logger.info("Test setup completed");
    }

    /**
     * Teardown method - executed after each test
     * Closes WebDriver and cleans up resources
     */
    @AfterMethod
    public void tearDown() {
        logger.info("Starting test teardown");
        DriverFactory.quitDriver();
        logger.info("Test teardown completed");
    }
}
