package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ConfigReader class loads properties from config.properties file
 */
public class ConfigReader {

    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static Properties properties;
    private static final String PROPERTIES_FILE = "src/main/resources/config.properties";

    static {
        try {
            properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream(PROPERTIES_FILE);
            properties.load(fileInputStream);
            fileInputStream.close();
            logger.info("Properties file loaded successfully");
        } catch (IOException e) {
            logger.error("Failed to load properties file: " + PROPERTIES_FILE, e);
        }
    }

    /**
     * Get property value by key
     *
     * @param key Property key
     * @return Property value
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property not found: " + key);
        }
        return value;
    }

    /**
     * Get URL from properties
     *
     * @return Application URL
     */
    public static String getApplicationUrl() {
        return getProperty("app.url");
    }

    /**
     * Get browser from properties
     *
     * @return Browser name
     */
    public static String getBrowser() {
        return getProperty("browser");
    }

    /**
     * Get implicit wait timeout
     *
     * @return Timeout in seconds
     */
    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait"));
    }

    /**
     * Get explicit wait timeout
     *
     * @return Timeout in seconds
     */
    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait"));
    }

    /**
     * Get page load timeout
     *
     * @return Timeout in seconds
     */
    public static int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("page.load.timeout"));
    }

    /**
     * Get headless mode flag
     *
     * @return true if headless mode is enabled
     */
    public static boolean isHeadlessMode() {
        return Boolean.parseBoolean(getProperty("headless.mode"));
    }

    /**
     * Get screenshot on failure flag
     *
     * @return true if screenshots should be taken on failure
     */
    public static boolean takeScreenshotOnFailure() {
        return Boolean.parseBoolean(getProperty("screenshot.on.failure"));
    }
}
