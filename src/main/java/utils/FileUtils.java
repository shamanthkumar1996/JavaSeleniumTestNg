package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * FileUtils class provides file handling utility methods
 */
public class FileUtils {

    private static final Logger logger = LogManager.getLogger(FileUtils.class);
    private static final String SCREENSHOT_PATH = "screenshots";
    private static final String REPORT_PATH = "test-output";

    /**
     * Create screenshot directory if not exists
     */
    public static void createScreenshotDirectory() {
        try {
            Files.createDirectories(Paths.get(SCREENSHOT_PATH));
            logger.info("Screenshot directory created/verified");
        } catch (IOException e) {
            logger.error("Failed to create screenshot directory", e);
        }
    }

    /**
     * Generate unique file name with timestamp
     *
     * @param prefix File name prefix
     * @return Unique file name
     */
    public static String generateUniqueFileName(String prefix) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return prefix + "_" + sdf.format(new Date());
    }

    /**
     * Get screenshot file path
     *
     * @param fileName File name
     * @return Full file path
     */
    public static String getScreenshotPath(String fileName) {
        return SCREENSHOT_PATH + File.separator + fileName + ".png";
    }

    /**
     * Check if file exists
     *
     * @param filePath File path
     * @return true if file exists, false otherwise
     */
    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * Delete file
     *
     * @param filePath File path
     * @return true if deletion successful, false otherwise
     */
    public static boolean deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                return file.delete();
            }
            return false;
        } catch (Exception e) {
            logger.error("Failed to delete file: " + filePath, e);
            return false;
        }
    }

    /**
     * Create directory
     *
     * @param dirPath Directory path
     * @return true if creation successful, false otherwise
     */
    public static boolean createDirectory(String dirPath) {
        try {
            Files.createDirectories(Paths.get(dirPath));
            logger.info("Directory created: " + dirPath);
            return true;
        } catch (IOException e) {
            logger.error("Failed to create directory: " + dirPath, e);
            return false;
        }
    }

    /**
     * Get file size
     *
     * @param filePath File path
     * @return File size in bytes
     */
    public static long getFileSize(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.length();
        }
        logger.warn("File not found: " + filePath);
        return 0;
    }

    /**
     * Get file extension
     *
     * @param fileName File name
     * @return File extension
     */
    public static String getFileExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');
        if (lastDot > 0) {
            return fileName.substring(lastDot + 1);
        }
        return "";
    }

    /**
     * Create new file
     *
     * @param filePath File path
     * @return true if creation successful, false otherwise
     */
    public static boolean createNewFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.createNewFile()) {
                logger.info("File created: " + filePath);
                return true;
            }
            return false;
        } catch (IOException e) {
            logger.error("Failed to create file: " + filePath, e);
            return false;
        }
    }
}
