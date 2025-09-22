package config;

import lombok.Getter;
import utils.PropertiesUtils;

@Getter
public class TestConfig {
    private static final ThreadLocal<TestConfig> threadLocalConfig = new ThreadLocal<>();
    private String platformName;
    private String automationName;
    private String app;
    private String udid; // For iOS
    private String deviceName; // For Android
    private int timeout;
    private boolean headless;
    private String appiumServerUrl;
    private TestInfo testInfo;
    private TestConfig() {}

    public static TestConfig load(String propName) {
        TestConfig config = new TestConfig();
        config.platformName = PropertiesUtils.getProperty(propName, "platformName").orElse("unknown");
        config.automationName = PropertiesUtils.getProperty(propName, "automationName").orElse("unknown");
        config.app = PropertiesUtils.getProperty(propName, "app").orElseThrow(() -> new IllegalStateException("App path required"));
        if ("ios".equals(propName)) {
            config.udid = PropertiesUtils.getProperty(propName, "udid").orElseThrow(() -> new IllegalStateException("UDID required for iOS"));
        } else if ("android".equals(propName)) {
            config.deviceName = PropertiesUtils.getProperty(propName, "deviceName").orElseThrow(() -> new IllegalStateException("Device name required for Android"));
        }
        config.timeout = PropertiesUtils.getPropertyAsInt(propName, "timeout", 30);
        config.headless = PropertiesUtils.getPropertyAsBoolean(propName, "headless", false);
        config.appiumServerUrl = PropertiesUtils.getProperty(propName,"URL").orElseThrow(() -> new IllegalStateException("Appium Server URL required "));
        threadLocalConfig.set(config);
        return config;
    }

    public static TestConfig get() {
        TestConfig config = threadLocalConfig.get();
        if (config == null) {
            throw new IllegalStateException("TestConfig not loaded for this thread");
        }
        return config;
    }
}

