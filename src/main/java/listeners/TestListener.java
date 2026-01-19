package listeners;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.BeforeSuite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import base.DriverFactory;
import utils.ConfigReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * TestListener class implements ITestListener for test execution monitoring
 * Handles test start, finish, failure, skip events
 * Generates screenshots on test failure
 * Dynamically generates environment.properties for Allure reports
 */
public class TestListener implements ITestListener {

    private static final Logger logger = LogManager.getLogger(TestListener.class);
    private static final String ALLURE_RESULTS_DIR = "allure-results";
    private static final String ENVIRONMENT_FILE = "environment.properties";

    /**
     * Generate environment.properties dynamically before suite execution
     */
    @BeforeSuite
    public void generateEnvironmentProperties() {
        try {
            // Create allure-results directory if it doesn't exist
            File allureDir = new File(ALLURE_RESULTS_DIR);
            if (!allureDir.exists()) {
                allureDir.mkdirs();
                logger.info("Created allure-results directory");
            }

            // Create environment properties file
            File envFile = new File(allureDir, ENVIRONMENT_FILE);
            try (FileWriter writer = new FileWriter(envFile)) {
                String osName = System.getProperty("os.name");
                String osVersion = System.getProperty("os.version");
                String javaVersion = System.getProperty("java.version");
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                writer.write("Browser=Chrome\n");
                writer.write("Browser.Version=Latest\n");
                writer.write("OS=" + osName + "\n");
                writer.write("OS.Version=" + osVersion + "\n");
                writer.write("Java.Version=" + javaVersion + "\n");
                writer.write("Selenium.Version=4.16.1\n");
                writer.write("TestNG.Version=7.9.0\n");
                writer.write("Allure.Version=2.25.0\n");
                writer.write("Framework=Selenium + TestNG + POM\n");
                writer.write("Base.URL=https://www.amazon.com\n");
                writer.write("Test.Environment=QA\n");
                writer.write("Reporter=Allure\n");
                writer.write("Execution.DateTime=" + timestamp + "\n");

                logger.info("Environment.properties file generated successfully at: " + envFile.getAbsolutePath());
            }
        } catch (IOException e) {
            logger.error("Failed to generate environment.properties file", e);
        }
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("================================");
        logger.info("Test Suite Started: " + context.getName());
        logger.info("================================");
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("================================");
        logger.info("Test Suite Finished: " + context.getName());
        logger.info("Total Tests: " + context.getAllTestMethods().length);
        logger.info("Passed Tests: " + context.getPassedTests().size());
        logger.info("Failed Tests: " + context.getFailedTests().size());
        logger.info("Skipped Tests: " + context.getSkippedTests().size());
        logger.info("================================");
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("***** Test Started: " + result.getName() + " *****");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("***** Test Passed: " + result.getName() + " *****");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("***** Test Failed: " + result.getName() + " *****");
        logger.error("Error Details: " + result.getThrowable().getMessage());

        if (DriverFactory.isDriverInitialized() && ConfigReader.takeScreenshotOnFailure()) {
            try {
                WebDriver driver = DriverFactory.getDriver();
                attachScreenshot(driver, result.getName());
                logger.info("Screenshot captured for failed test: " + result.getName());
            } catch (Exception e) {
                logger.error("Failed to capture screenshot", e);
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("***** Test Skipped: " + result.getName() + " *****");
        logger.warn("Skip Reason: Test was skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.info("Test Failed But Within Success Percentage: " + result.getName());
    }

    /**
     * Capture screenshot and attach to Allure report
     *
     * @param driver WebDriver instance
     * @param testName Test name
     */
    @Attachment(value = "Screenshot - {testName}", type = "image/png")
    private byte[] attachScreenshot(WebDriver driver, String testName) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Attach text to Allure report
     *
     * @param message Message to attach
     */
    @Attachment(value = "Info", type = "text/plain")
    protected String attachMessage(String message) {
        return message;
    }
}

