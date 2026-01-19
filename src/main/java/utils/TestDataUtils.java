package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * TestDataUtils class provides test data generation utilities
 */
public class TestDataUtils {

    private static final Logger logger = LogManager.getLogger(TestDataUtils.class);
    private static final Random random = new Random();

    /**
     * Generate random email
     *
     * @return Random email address
     */
    public static String generateRandomEmail() {
        String email = "user_" + System.currentTimeMillis() + "@test.com";
        logger.info("Generated email: " + email);
        return email;
    }

    /**
     * Generate random string
     *
     * @param length String length
     * @return Random string
     */
    public static String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }
        return result.toString();
    }

    /**
     * Generate random number
     *
     * @param min Minimum value
     * @param max Maximum value
     * @return Random number
     */
    public static int generateRandomNumber(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Generate random password
     *
     * @return Strong password
     */
    public static String generateRandomPassword() {
        String password = "Pass@" + generateRandomString(8) + generateRandomNumber(100, 999);
        logger.info("Generated password");
        return password;
    }

    /**
     * Get current timestamp
     *
     * @return Current timestamp as string
     */
    public static String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * Generate random phone number
     *
     * @return Random phone number
     */
    public static String generateRandomPhoneNumber() {
        return "9" + generateRandomNumber(100000000, 999999999);
    }

    /**
     * Generate random product name
     *
     * @return Random product name
     */
    public static String generateRandomProductName() {
        String[] products = {"Laptop", "Phone", "Tablet", "Monitor", "Keyboard", "Mouse", "Headphones", "Speaker"};
        String[] brands = {"Dell", "HP", "Lenovo", "Apple", "Samsung", "Sony", "Logitech", "Corsair"};
        return brands[random.nextInt(brands.length)] + " " + products[random.nextInt(products.length)];
    }

    /**
     * Generate random price
     *
     * @param min Minimum price
     * @param max Maximum price
     * @return Random price
     */
    public static double generateRandomPrice(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    /**
     * Generate random rating
     *
     * @return Random rating (1-5)
     */
    public static double generateRandomRating() {
        return Math.round(generateRandomNumber(1, 5) + random.nextDouble() * 10) / 10.0;
    }
}
