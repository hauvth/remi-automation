package driver;

import config.TestConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class IOSPlatformDriver {
    private static final ThreadLocal<IOSDriver> threadLocalDriver = new ThreadLocal<>();

    public static IOSDriver initialize(String appiumServerUrl) {
        TestConfig config = TestConfig.load("IOS");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", config.getPlatformName());
        capabilities.setCapability("appium:automationName", config.getAutomationName());
        capabilities.setCapability("appium:udid", config.getUdid());
        capabilities.setCapability("appium:app", config.getApp());
        capabilities.setCapability("appium:isHeadless",config.getTestInfo().isHeadless());
        try {
            IOSDriver driver = new IOSDriver(new URL(appiumServerUrl), capabilities);
            threadLocalDriver.set(driver);
            return driver;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium URL: " + e.getMessage(), e);
        }
    }
    public static IOSDriver get() {
        IOSDriver driver = threadLocalDriver.get();
        if (driver == null) {
            throw new IllegalStateException("IOSDriver not initialized for this thread");
        }
        return driver;
    }
}
