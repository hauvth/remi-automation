package config;

import lombok.Getter;
import lombok.Setter;
import utils.PropertiesUtils;

@Getter
@Setter
public class TestConfig {
    private static final ThreadLocal<TestConfig> threadLocalConfig = new ThreadLocal<>();
    private int timeout;
    private boolean headless;
    private int retryCount;
    private String platform;
    private TestConfig() {}

    public static TestConfig load() {
        TestConfig config = new TestConfig();
        PropertiesUtils propertiesUtils = new PropertiesUtils("src/test/resources/config.properties");
        config.timeout = propertiesUtils.getIntProperty("timeout",10);
        config.headless = propertiesUtils.getBooleanProperty( "headless", false);
        config.retryCount = propertiesUtils.getIntProperty("retry",2);
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

