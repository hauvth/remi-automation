package hooks;

import config.TestConfig;
import driver.DriverFactory;
import driver.DriverManager;
import io.cucumber.java.*;
import report.ExtentReportManager;

public class CucumberHooks {

    @BeforeAll
    public static void beforeAll() {
        String platform = System.getProperty("platform","ANDROID");
        TestConfig testConfig = TestConfig.load();
        testConfig.setPlatform(platform);
        DriverFactory.createDriver(platform);
        ExtentReportManager.initReports();
    }
    @AfterAll
    public static void afterAll() {
        ExtentReportManager.flushReports();
    }
    @Before
    public void before(io.cucumber.java.Scenario scenario ) {
        ExtentReportManager.createCucumberTest(scenario);
    }
    @After
    public void after() {
        DriverManager.quit();
    }
}
