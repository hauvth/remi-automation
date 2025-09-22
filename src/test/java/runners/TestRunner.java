package runners;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import report.ExtentReportManager;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.example.stepdefs", "com.example.hooks"},
        plugin = {"pretty", "html:target/cucumber-reports"},
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

}
