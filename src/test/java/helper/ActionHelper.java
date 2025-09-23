package helper;

import driver.DriverManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Arrays;

public class ActionHelper {
    private final AppiumDriver driver = DriverManager.getDriver();
    private final PointerInput pointer = new PointerInput(PointerInput.Kind.TOUCH, "finger");
    private ActionHelper(AppiumDriver driver) {

    }

    // Tap at specific coordinates
    public void tap(int x, int y) {
        Sequence tap = new Sequence(pointer, 0)
                .addAction(pointer.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y))
                .addAction(pointer.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(pointer.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(tap));
    }

    // Tap on an element
    public void tap(WebElement element) {
        Point center = getElementCenter(element);
        tap(center.x, center.y);
    }

    // Swipe from one point to another
    public void swipe(int startX, int startY, int endX, int endY, int durationMs) {
        Sequence swipe = new Sequence(pointer, 0)
                .addAction(pointer.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                .addAction(pointer.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(pointer.createPointerMove(Duration.ofMillis(durationMs), PointerInput.Origin.viewport(), endX, endY))
                .addAction(pointer.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(swipe));
    }

    // Swipe by percentage of screen (0.0 to 1.0)
    public void swipeByPercentage(double startXPercent, double startYPercent, double endXPercent, double endYPercent, int durationMs) {
        Dimension screenSize = driver.manage().window().getSize();
        int startX = (int) (screenSize.width * startXPercent);
        int startY = (int) (screenSize.height * startYPercent);
        int endX = (int) (screenSize.width * endXPercent);
        int endY = (int) (screenSize.height * endYPercent);
        swipe(startX, startY, endX, endY, durationMs);
    }

    // Long press at specific coordinates
    public void longPress(int x, int y, int durationMs) {
        Sequence longPress = new Sequence(pointer, 0)
                .addAction(pointer.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y))
                .addAction(pointer.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(pointer.createPointerMove(Duration.ofMillis(durationMs), PointerInput.Origin.viewport(), x, y))
                .addAction(pointer.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(longPress));
    }

    // Long press on an element
    public void longPress(WebElement element, int durationMs) {
        Point center = getElementCenter(element);
        longPress(center.x, center.y, durationMs);
    }

    // Pinch to zoom out (using two fingers)
    public void pinch(WebElement element, double scale, int durationMs) {
        Point center = getElementCenter(element);
        int offset = 100; // Distance for fingers movement

        Sequence finger1 = new Sequence(new PointerInput(PointerInput.Kind.TOUCH, "finger1"), 0)
                .addAction(pointer.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), center.x - offset, center.y))
                .addAction(pointer.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(pointer.createPointerMove(Duration.ofMillis(durationMs), PointerInput.Origin.viewport(), center.x - (int)(offset * scale), center.y))
                .addAction(pointer.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        Sequence finger2 = new Sequence(new PointerInput(PointerInput.Kind.TOUCH, "finger2"), 0)
                .addAction(pointer.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), center.x + offset, center.y))
                .addAction(pointer.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(pointer.createPointerMove(Duration.ofMillis(durationMs), PointerInput.Origin.viewport(), center.x + (int)(offset * scale), center.y))
                .addAction(pointer.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Arrays.asList(finger1, finger2));
    }

    // Zoom in (using two fingers)
    public void zoom(WebElement element, double scale, int durationMs) {
        Point center = getElementCenter(element);
        int offset = 100; // Distance for fingers movement

        Sequence finger1 = new Sequence(new PointerInput(PointerInput.Kind.TOUCH, "finger1"), 0)
                .addAction(pointer.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), center.x - (int)(offset * scale), center.y))
                .addAction(pointer.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(pointer.createPointerMove(Duration.ofMillis(durationMs), PointerInput.Origin.viewport(), center.x - offset, center.y))
                .addAction(pointer.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        Sequence finger2 = new Sequence(new PointerInput(PointerInput.Kind.TOUCH, "finger2"), 0)
                .addAction(pointer.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), center.x + (int)(offset * scale), center.y))
                .addAction(pointer.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(pointer.createPointerMove(Duration.ofMillis(durationMs), PointerInput.Origin.viewport(), center.x + offset, center.y))
                .addAction(pointer.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Arrays.asList(finger1, finger2));
    }

    // Scroll to element by swiping up
    public void scrollToElement(WebElement element, int maxSwipes) {
        Dimension screenSize = driver.manage().window().getSize();
        int startY = (int) (screenSize.height * 0.8);
        int endY = (int) (screenSize.height * 0.2);
        int centerX = screenSize.width / 2;

        for (int i = 0; i < maxSwipes; i++) {
            if (isElementVisible(element)) {
                return;
            }
            swipe(centerX, startY, centerX, endY, 1000);
        }
    }

    // Helper method to get element center point
    private Point getElementCenter(WebElement element) {
        Point location = element.getLocation();
        Dimension size = element.getSize();
        return new Point(location.x + size.width / 2, location.y + size.height / 2);
    }

    // Helper method to check if element is visible
    private boolean isElementVisible(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
