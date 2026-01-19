package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import io.qameta.allure.Attachment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ScreenshotUtils class provides screenshot capture functionality
 */
public class ScreenshotUtils {

    private static final Logger logger = LogManager.getLogger(ScreenshotUtils.class);
    private static final String SCREENSHOT_DIR = "screenshots";

    /**
     * Capture screenshot as file
     *
     * @param driver WebDriver instance
     * @param fileName Screenshot file name
     * @return File path
     */
    public static String captureScreenshot(WebDriver driver, String fileName) {
        try {
            // Create screenshot directory
            File directory = new File(SCREENSHOT_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Capture screenshot
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);

            // Save screenshot file
            String filePath = SCREENSHOT_DIR + File.separator + fileName + ".png";
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(screenshotBytes);
            fos.close();

            logger.info("Screenshot captured: " + filePath);
            return filePath;

        } catch (IOException e) {
            logger.error("Failed to capture screenshot", e);
            return null;
        }
    }

    /**
     * Capture and attach screenshot to Allure report
     *
     * @param driver WebDriver instance
     * @param screenshotName Screenshot name
     */
    @Attachment(value = "Screenshot - {screenshotName}", type = "image/png")
    public static byte[] captureScreenshotForAllure(WebDriver driver, String screenshotName) {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            logger.error("Failed to capture screenshot for Allure", e);
            return new byte[0];
        }
    }

    /**
     * Capture screenshot on test failure
     *
     * @param driver WebDriver instance
     * @param testName Test name
     */
    public static void captureScreenshotOnFailure(WebDriver driver, String testName) {
        try {
            String fileName = "FAILED_" + testName + "_" + System.currentTimeMillis();
            captureScreenshot(driver, fileName);
            logger.info("Failure screenshot captured for test: " + testName);
        } catch (Exception e) {
            logger.error("Failed to capture failure screenshot", e);
        }
    }

    /**
     * Capture full page screenshot using JavaScript
     *
     * @param driver WebDriver instance
     * @param fileName Screenshot file name
     * @return File path
     */
    public static String captureFullPageScreenshot(WebDriver driver, String fileName) {
        try {
            // Scroll to top
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, 0);");

            // Capture screenshot
            return captureScreenshot(driver, fileName);

        } catch (Exception e) {
            logger.error("Failed to capture full page screenshot", e);
            return null;
        }
    }

    /**
     * Capture screenshot of a specific element
     *
     * @param driver WebDriver instance
     * @param element WebElement to capture
     * @param fileName Screenshot file name
     * @return File path
     */
    public static String captureElementScreenshot(WebDriver driver, org.openqa.selenium.WebElement element, String fileName) {
        try {
            byte[] screenshotBytes = element.getScreenshotAs(OutputType.BYTES);

            File directory = new File(SCREENSHOT_DIR);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filePath = SCREENSHOT_DIR + File.separator + fileName + ".png";
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(screenshotBytes);
            fos.close();

            logger.info("Element screenshot captured: " + filePath);
            return filePath;

        } catch (IOException e) {
            logger.error("Failed to capture element screenshot", e);
            return null;
        }
    }

    /**
     * Delete screenshot file
     *
     * @param filePath File path to delete
     * @return true if deletion successful, false otherwise
     */
    public static boolean deleteScreenshot(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                return file.delete();
            }
            return false;
        } catch (Exception e) {
            logger.error("Failed to delete screenshot: " + filePath, e);
            return false;
        }
    }
}
