package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesUtils {

    private static final Logger LOGGER = Logger.getLogger(PropertiesUtils.class.getName());
    private static Properties properties;
    private static String filePath;
    private static boolean isInitialized = false;

    public static void initialize(String filePath) {
        if (isInitialized && PropertiesUtils.filePath.equals(filePath)) {
            LOGGER.info("Properties already initialized with file: " + filePath);
            return;
        }
        PropertiesUtils.filePath = filePath;
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
            isInitialized = true;
            LOGGER.info("Successfully loaded properties file: " + filePath);
        } catch (IOException e) {
            LOGGER.severe("Failed to load properties file: " + filePath + ". Error: " + e.getMessage());
            throw new RuntimeException("Could not load properties file: " + filePath, e);
        }
    }
    private static void checkInitialization() {
        if (!isInitialized) {
            throw new IllegalStateException("PropertiesReader is not initialized. Call initialize(String filePath) first.");
        }
    }
    public static Optional<String> getProperty(String key) {
        checkInitialization();
        String value = properties.getProperty(key);
        if (value == null) {
            LOGGER.warning("Property key '" + key + "' not found in " + filePath);
            return Optional.empty();
        }
        return Optional.of(value);
    }
    public static String getProperty(String key, String defaultValue) {
        checkInitialization();
        return properties.getProperty(key, defaultValue);
    }
    public static Optional<Integer> getPropertyAsInt(String key) {
        checkInitialization();
        String value = properties.getProperty(key);
        if (value == null) {
            LOGGER.warning("Property key '" + key + "' not found in " + filePath);
            return Optional.empty();
        }
        try {
            return Optional.of(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            LOGGER.warning("Invalid integer format for key '" + key + "' with value '" + value + "'");
            return Optional.empty();
        }
    }
    public static int getPropertyAsInt(String key, int defaultValue) {
        checkInitialization();
        String value = properties.getProperty(key);
        if (value == null) {
            LOGGER.warning("Property key '" + key + "' not found in " + filePath);
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            LOGGER.warning("Invalid integer format for key '" + key + "' with value '" + value + "'");
            return defaultValue;
        }
    }
    public static boolean hasProperty(String key) {
        checkInitialization();
        return properties.containsKey(key);
    }
    public static Optional<Boolean> getPropertyAsBoolean(String key) {
        checkInitialization();
        String value = properties.getProperty(key);
        if (value == null) {
            LOGGER.warning("Property key '" + key + "' not found in " + filePath);
            return Optional.empty();
        }
        if (value.trim().equalsIgnoreCase("true") || value.trim().equalsIgnoreCase("false")) {
            return Optional.of(Boolean.parseBoolean(value));
        }
        LOGGER.warning("Invalid boolean format for key '" + key + "' with value '" + value + "'");
        return Optional.empty();
    }
    public static boolean getPropertyAsBoolean(String key, boolean defaultValue) {
        checkInitialization();
        String value = properties.getProperty(key);
        if (value == null) {
            LOGGER.warning("Property key '" + key + "' not found in " + filePath);
            return defaultValue;
        }
        if (value.trim().equalsIgnoreCase("true") || value.trim().equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(value);
        }
        LOGGER.warning("Invalid boolean format for key '" + key + "' with value '" + value + "'");
        return defaultValue;
    }
}
