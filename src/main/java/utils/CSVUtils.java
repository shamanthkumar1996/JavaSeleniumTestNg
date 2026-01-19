package utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CSVUtils class handles reading and processing CSV data files
 * Used for data-driven testing
 */
public class CSVUtils {

    private static final Logger logger = LogManager.getLogger(CSVUtils.class);

    /**
     * Read CSV file and return data as List of Maps
     * First row is treated as headers
     *
     * @param filePath Path to CSV file
     * @return List of Maps containing CSV data
     */
    public static List<Map<String, String>> readCSVFile(String filePath) {
        List<Map<String, String>> data = new ArrayList<>();
        String[] headers = null;

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] nextRecord;
            boolean firstRow = true;

            while ((nextRecord = csvReader.readNext()) != null) {
                if (firstRow) {
                    headers = nextRecord;
                    firstRow = false;
                } else {
                    Map<String, String> rowData = new HashMap<>();
                    for (int i = 0; i < headers.length; i++) {
                        rowData.put(headers[i].trim(), nextRecord[i].trim());
                    }
                    data.add(rowData);
                }
            }
            logger.info("CSV file read successfully: " + filePath + " with " + data.size() + " rows");

        } catch (IOException | CsvValidationException e) {
            logger.error("Error reading CSV file: " + filePath, e);
        }

        return data;
    }

    /**
     * Get CSV data for TestNG DataProvider
     * Converts List of Maps to Object[][] format
     *
     * @param filePath Path to CSV file
     * @return Object array for TestNG DataProvider
     */
    public static Object[][] getCSVDataForTestNG(String filePath) {
        List<Map<String, String>> csvData = readCSVFile(filePath);
        Object[][] data = new Object[csvData.size()][1];

        for (int i = 0; i < csvData.size(); i++) {
            data[i][0] = csvData.get(i);
        }

        return data;
    }

    /**
     * Get specific column data from CSV file
     *
     * @param filePath Path to CSV file
     * @param columnName Column name to extract
     * @return List of values from the specified column
     */
    public static List<String> getCSVColumnData(String filePath, String columnName) {
        List<Map<String, String>> csvData = readCSVFile(filePath);
        List<String> columnData = new ArrayList<>();

        for (Map<String, String> row : csvData) {
            columnData.add(row.get(columnName));
        }

        return columnData;
    }

    /**
     * Get row count from CSV file
     *
     * @param filePath Path to CSV file
     * @return Number of data rows (excluding header)
     */
    public static int getRowCount(String filePath) {
        return readCSVFile(filePath).size();
    }

    /**
     * Get column count from CSV file
     *
     * @param filePath Path to CSV file
     * @return Number of columns
     */
    public static int getColumnCount(String filePath) {
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] headers = csvReader.readNext();
            return headers != null ? headers.length : 0;
        } catch (IOException | CsvValidationException e) {
            logger.error("Error reading CSV file: " + filePath, e);
            return 0;
        }
    }
}
