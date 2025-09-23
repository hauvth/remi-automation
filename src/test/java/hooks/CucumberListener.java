package hooks;


import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.*;
import report.ExtentReportManager;
import utils.LogUtils;

public class CucumberListener implements EventListener {

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        //Set all events that need use (public). Override from EventListener
        publisher.registerHandlerFor(TestRunStarted.class, this::runStarted);
        publisher.registerHandlerFor(TestRunFinished.class, this::runFinished);
        publisher.registerHandlerFor(TestSourceRead.class, this::featureRead);
        publisher.registerHandlerFor(TestCaseStarted.class, this::ScenarioStarted);
        publisher.registerHandlerFor(TestCaseFinished.class, this::ScenarioFinished);
        publisher.registerHandlerFor(TestStepStarted.class, this::stepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::stepFinished);
    }

    private void runStarted(TestRunStarted event) {
        LogUtils.info("********* RUN STARTED *********");
    }

    private void runFinished(TestRunFinished event) {
        LogUtils.info("********* RUN FINISHED *********");
    }

    private void featureRead(TestSourceRead event) {
        String featurePath = event.getUri().toString();
        String featureName = featurePath.split(".*/")[1];
        LogUtils.info("Feature Name: " + featureName);
    }

    private void ScenarioStarted(TestCaseStarted event) {
        LogUtils.info("Scenario Path: " + event.getTestCase().getUri().toString());
        LogUtils.info("Scenario Name: " + event.getTestCase().getName());
    }

    private void ScenarioFinished(TestCaseFinished event) {
        LogUtils.info("Scenario Status: " + event.getResult().getStatus());
    }
    private void stepStarted(TestStepStarted event) {
        if (event.getTestStep() instanceof PickleStepTestStep testStep) {
            LogUtils.info("Step: " + testStep.getStep().getText());
            ExtentReportManager.info(testStep.getStep().getText());
        }
    }
    private void stepFinished(TestStepFinished event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            LogUtils.info("Step Status: " + event.getResult().getStatus());
            if (event.getResult().getStatus() == Status.FAILED) {
                LogUtils.error(event.getResult().getError());
                ExtentReportManager.logMessage(com.aventstack.extentreports.Status.FAIL, event.getResult().getError().toString());
            } else if (event.getResult().getStatus() == Status.PASSED) {
                ExtentReportManager.logMessage(com.aventstack.extentreports.Status.PASS, "Step passed");
            } else if (event.getResult().getStatus() == Status.SKIPPED) {
                ExtentReportManager.logMessage(com.aventstack.extentreports.Status.SKIP, "Step skipped");
            }
        }
    }
}
