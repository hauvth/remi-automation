package config;

import lombok.Getter;
import utils.PropertiesUtils;

@Getter
public class TestInfo {
    private int timeOut;
    private boolean isScreenShot;
    private int retry;
    private String platform;
    private boolean isParallel;
    private boolean isHeadless;

    public TestInfo(){
        PropertiesUtils.initialize("testInfo","src/main/resources/testInfo.properties");
        this.timeOut = PropertiesUtils.getPropertyAsInt("testInfo","timeOut",5);
        this.isScreenShot = PropertiesUtils.getPropertyAsBoolean("testInfo","timeOut",true);
        this.retry = PropertiesUtils.getPropertyAsInt("testInfo","timeOut",5);
        this.platform = PropertiesUtils.getPropertyAsString("testInfo","platform","ANDROID");
        this.isParallel = PropertiesUtils.getPropertyAsBoolean("testInfo","timeOut",true);
    }
}
