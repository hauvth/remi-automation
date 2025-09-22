package driver;

import io.appium.java_client.AppiumDriver;

public class DriverFactory {
    public static void createDriver(String platform){
        AppiumDriver appiumDriver = switch (platform) {
            case "IOS" -> IOSPlatformDriver.initialize();
            case "ANDROID" -> AndroidPlatformDriver.initialize();
            default -> throw new IllegalArgumentException("Invalid Platform");
        };
        try {
            DriverManager.setDriver(appiumDriver);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
