package report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.cucumber.java.Scenario;

import java.util.Objects;

public class ExtentReportManager {
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static ExtentReports extentReports;

    public static void initReports() {
        if (extentReports == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("target/extent-reports/ExtentReport.html");
            spark.config().setReportName("Cucumber-TestNG Automation Report");
            spark.config().setDocumentTitle("Test Execution Report");
            extentReports = new ExtentReports();
            extentReports.attachReporter(spark);
            extentReports.setSystemInfo("OS", System.getProperty("os.name"));
            extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
        }
    }

    public static void flushReports() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }

    public static void createTest(String testName) {
        ExtentTest test = extentReports.createTest(testName);
        extentTest.set(test);
    }

    public static void createCucumberTest(Scenario scenario) {
        ExtentTest test = extentReports.createTest(scenario.getName());
        test.assignCategory(scenario.getSourceTagNames().toString());
        extentTest.set(test);
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void logMessage(Status status, String message) {
        if (getTest() != null) {
            getTest().log(status, message);
        }
    }

    public static void info(String message) {
        if (getTest() != null) {
            getTest().info( message);
        }
    }
}
