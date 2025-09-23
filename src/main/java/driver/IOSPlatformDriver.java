package driver;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


import java.net.MalformedURLException;
import java.net.URL;

public class IOSPlatformDriver {

    public static IOSDriver initialize() {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "IOS");
        capabilities.setCapability("appium:automationName", "XCUITest");
        capabilities.setCapability("appium:udid", "auto");
        capabilities.setCapability("appium:app", "");
        capabilities.setCapability("appium:isHeadless","false");
        capabilities.setCapability("waitForSelectorTimeout", 15000);
        try {
            return new IOSDriver(new URL(""), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium URL: " + e.getMessage(), e);
        }
    }
}
