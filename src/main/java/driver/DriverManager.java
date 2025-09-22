package driver;

import io.appium.java_client.AppiumDriver;

public class DriverManager {
    private static final ThreadLocal<AppiumDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverManager() {
        super();
    }
    public static AppiumDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void setDriver(AppiumDriver driver) {
        DriverManager.driverThreadLocal.set(driver);
    }

    public static void quit() {
        if (DriverManager.getDriver() != null){
            DriverManager.getDriver().quit();
            driverThreadLocal.remove();
        }
    }
}
