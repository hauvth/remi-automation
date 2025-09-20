package driver;

import config.TestConfig;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class AndroidPlatformDriver {
    private static final ThreadLocal<AndroidDriver> threadLocalDriver = new ThreadLocal<>();
    public static AndroidDriver initialize(String appiumServerUrl) {
        TestConfig config = TestConfig.load("android"); // Load config for Android

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", config.getPlatformName());
        capabilities.setCapability("appium:automationName", config.getAutomationName());
        capabilities.setCapability("appium:isHeadless",config.getTestInfo().isHeadless());
        capabilities.setCapability("appium:app", config.getApp());
        capabilities.setCapability("waitForSelectorTimeout", config.getTestInfo().getTimeOut());
        try {
            AndroidDriver driver = new AndroidDriver(new URL(appiumServerUrl), capabilities);
            threadLocalDriver.set(driver);
            return driver;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium URL: " + e.getMessage(), e);
        }
    }

    public static AndroidDriver get() {
        AndroidDriver driver = threadLocalDriver.get();
        if (driver == null) {
            throw new IllegalStateException("AndroidDriver not initialized for this thread");
        }
        return driver;
    }
}
