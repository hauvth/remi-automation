package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesUtils {

    private static final Logger LOGGER = Logger.getLogger(PropertiesUtils.class.getName());
    private static final Map<String, Properties> propertiesMap = new HashMap<>(); // Map for platform -> Properties
    private static final Map<String, Boolean> initializedFiles = new HashMap<>();
    private static final String IOS_PROP_FILE = "src/test/resources/ios.properties";
    private static final String ANDROID_PROP_FILE = "src/test/resources/android.properties";
    private static final String TESTINFO_PROP_FILE = "src/test/resources/testInfo.properties";
    public static synchronized void initialize(String propName) {
        if (initializedFiles.getOrDefault(propName, false)) {
            LOGGER.info("Properties already initialized for platform: " + propName);
            return;
        }
        String filePath;
        if ("IOS".equalsIgnoreCase(propName)) {
            filePath = IOS_PROP_FILE;
        } else if ("ANDROID".equalsIgnoreCase(propName)) {
            filePath = ANDROID_PROP_FILE;
        } else if ("testInfo".equalsIgnoreCase(propName)) {
            filePath = TESTINFO_PROP_FILE;
        } else {
            throw new IllegalArgumentException("Unknown properties name: " + propName);
        }
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
            propertiesMap.put(propName, properties);
            initializedFiles.put(propName, true);
            LOGGER.info("Loaded properties for " + propName + " from " + filePath);
        } catch (IOException e) {
            LOGGER.severe("Failed to load properties for " + propName + ": " + e.getMessage());
            throw new RuntimeException("Could not load properties file for " + propName, e);
        }
    }

    public static Optional<String> getProperty(String platform, String key) {
        checkInitialization(platform);
        String value = propertiesMap.get(platform).getProperty(key);
        return value != null ? Optional.of(value) : Optional.empty();
    }

    public static String getPropertyAsString(String propName, String key, String defaultValue) {
        checkInitialization(propName);
        String value = propertiesMap.get(propName).getProperty(key);
        if (value == null) return defaultValue;
        try {
            return value;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static int getPropertyAsInt(String propName, String key, int defaultValue) {
        checkInitialization(propName);
        String value = propertiesMap.get(propName).getProperty(key);
        if (value == null) return defaultValue;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static boolean getPropertyAsBoolean(String platform, String key, boolean defaultValue) {
        checkInitialization(platform);
        String value = propertiesMap.get(platform).getProperty(key);
        if (value == null) return defaultValue;
        return Boolean.parseBoolean(value);
    }

    private static void checkInitialization(String platform) {
        if (!initializedFiles.getOrDefault(platform, false)) {
            throw new IllegalStateException("Properties not initialized for platform: " + platform);
        }
    }
}
