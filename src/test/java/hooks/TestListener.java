package hooks;

import com.aventstack.extentreports.Status;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import report.ExtentReportManager;
import utils.LogUtils;

public class TestListener implements ITestListener, ISuiteListener {

    private String getTestName(ITestResult result) {
        return result.getTestName() != null ? result.getTestName() : result.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onStart(ISuite suite) {
       LogUtils.info("Starting Suite: " + suite.getName());
        ExtentReportManager.initReports();
    }

    @Override
    public void onFinish(ISuite suite) {
        LogUtils.info("End Suite: " + suite.getName());
        ExtentReportManager.flushReports();
    }

    @Override
    public void onTestStart(ITestResult result) {
        String browser = result.getTestContext().getCurrentXmlTest().getParameter("BROWSER") != null ?
                result.getTestContext().getCurrentXmlTest().getParameter("BROWSER") : "chrome";
        ExtentReportManager.createTest(getTestName(result));
//        LogUtils.info("Test case: " + getTestDescription(result) + " is starting...");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
//        LogUtils.info("Test case: " + getTestDescription(result) + " is passed.");
        ExtentReportManager.logMessage(Status.PASS, "Test case: " + getTestName(result) + " is passed.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
//        LogUtils.error(result.getThrowable());
        ExtentReportManager.logMessage(Status.FAIL, "Test case: " + getTestName(result) + " is failed.");
        ExtentReportManager.logMessage(Status.FAIL, result.getThrowable().toString());
//        ExtentReportManager.addScreenshot(getTestName(result));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
//        LogUtils.warn("Test case: " + getTestDescription(result) + " is skipped.");
        ExtentReportManager.logMessage(Status.SKIP, "Test case: " + getTestName(result) + " is skipped.");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
//        LogUtils.error("Test failed but within success ratio: " + getTestDescription(result));
        ExtentReportManager.logMessage(Status.WARNING, "Test failed but within success ratio: " + getTestName(result));
    }
}
