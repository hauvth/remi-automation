package helper;

import config.TestConfig;
import driver.DriverManager;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class DriverHelper {

    public static AppiumDriver getDriver() {
        return DriverManager.getDriver();
    }

    public Capabilities getCapabilities() {
        return getDriver().getCapabilities();
    }

    public String getTitle() {
        return getDriver().getTitle();
    }

    public void deleteDownloadableFiles(){
        DriverManager.getDriver().deleteDownloadableFiles();
    }

    public static WebDriverWait getWait(int timeOut) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(timeOut));
    }

    public WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(TestConfig.get().getTimeout()));
    }
}
