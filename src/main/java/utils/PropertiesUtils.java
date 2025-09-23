package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesUtils {

    private Properties properties;

    public PropertiesUtils(String filePath) {
        properties = new Properties();
        loadProperties(filePath);
    }

    private void loadProperties(String filePath) {
        try (InputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Không thể đọc file properties: " + filePath, e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public int getIntProperty(String key) {
        String value = getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Key không tồn tại: " + key);
        }
        return Integer.parseInt(value);
    }

    public int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        return value != null ? Integer.parseInt(value) : defaultValue;
    }

    public boolean getBooleanProperty(String key) {
        String value = getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Key không tồn tại: " + key);
        }
        return Boolean.parseBoolean(value);
    }


    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    public boolean containsKey(String key) {
        return properties.containsKey(key);
    }
}
