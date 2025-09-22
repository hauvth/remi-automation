package driver;

import config.TestConfig;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class AndroidPlatformDriver {
    public static AndroidDriver initialize() {
        TestConfig config = TestConfig.load("android"); // Load config for Android

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", config.getPlatformName());
        capabilities.setCapability("appium:automationName", config.getAutomationName());
        capabilities.setCapability("appium:isHeadless",config.getTestInfo().isHeadless());
        capabilities.setCapability("appium:app", config.getApp());
        capabilities.setCapability("waitForSelectorTimeout", config.getTestInfo().getTimeOut());
        try {
            return new AndroidDriver(new URL(config.getAppiumServerUrl()), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium URL: " + e.getMessage(), e);
        }
    }
}
