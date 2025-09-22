package hooks;

import driver.DriverFactory;
import driver.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;

public class CucumberHooks {

    @Before
    @Parameters("browser")
    public void setUp(ITestContext context) {
        String browser = context.getCurrentXmlTest().getParameter("browser");
        if (browser == null || browser.isEmpty()) {
            browser = System.getProperty("browser", "chrome");
        }
        DriverFactory.createDriver(browser);
        DriverManager.getDriver().manage().window().maximize();
    }

    @After
    public void tearDown() {
        DriverManager.quit();
    }
}
