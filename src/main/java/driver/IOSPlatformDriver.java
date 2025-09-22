package driver;

import config.TestConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class IOSPlatformDriver {

    public static IOSDriver initialize() {
        TestConfig config = TestConfig.load("IOS");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", config.getPlatformName());
        capabilities.setCapability("appium:automationName", config.getAutomationName());
        capabilities.setCapability("appium:udid", config.getUdid());
        capabilities.setCapability("appium:app", config.getApp());
        capabilities.setCapability("appium:isHeadless",config.getTestInfo().isHeadless());
        try {
            return new IOSDriver(new URL(config.getAppiumServerUrl()), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium URL: " + e.getMessage(), e);
        }
    }
}
