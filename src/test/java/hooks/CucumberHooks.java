package hooks;

import com.aventstack.extentreports.ExtentReports;
import config.TestConfig;
import driver.DriverFactory;
import driver.DriverManager;
import io.cucumber.core.gherkin.Step;
import io.cucumber.java.*;
import org.testng.ITestContext;
import report.ExtentReportManager;
import utils.PropertiesUtils;

public class CucumberHooks {

    @Before
    public static void beforeAll() {
        ExtentReportManager.initReports();
    }
    @AfterAll
    public static void afterAll() {
        ExtentReportManager.flushReports();
    }
    @Before
    public void beforeScenario(ITestContext context, Scenario scenario) {
        String browser = context.getCurrentXmlTest().getParameter("BROWSER");
        if (browser == null || browser.isEmpty()) {
            browser = System.getProperty("BROWSER", "ANDROID");
        }
        DriverFactory.createDriver(browser);
        DriverManager.getDriver().manage().window().maximize();
        TestConfig testConfig = TestConfig.load();
        testConfig.setPlatform(browser);
        ExtentReportManager.createCucumberTest(scenario);
    }
    @After
    public void afterScenario() {
        DriverManager.quit();
    }



}
