package utils;

import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

public class AssertionUtils {

    private SoftAssert softAssert;
    private List<String> errorMessages;

    public AssertionUtils() {
        this.softAssert = new SoftAssert();
        this.errorMessages = new ArrayList<>();
    }

    public void assertTrue(boolean condition, String message) {
        try {
            softAssert.assertTrue(condition, message);
            if (!condition) {
                errorMessages.add(message);
            }
        } catch (AssertionError e) {
            errorMessages.add(message + " | Exception: " + e.getMessage());
        }
    }

    // Kiểm tra sự bằng nhau của hai giá trị
    public void assertEquals(Object actual, Object expected, String message) {
        try {
            softAssert.assertEquals(actual, expected, message);
            if (!actual.equals(expected)) {
                errorMessages.add(message);
            }
        } catch (AssertionError e) {
            errorMessages.add(message + " | Exception: " + e.getMessage());
        }
    }

    public void assertNotEquals(Object actual, Object expected, String message) {
        try {
            softAssert.assertNotEquals(actual, expected, message);
            if (actual.equals(expected)) {
                errorMessages.add(message);
            }
        } catch (AssertionError e) {
            errorMessages.add(message + " | Exception: " + e.getMessage());
        }
    }

    public void assertCustom(boolean condition, String message) {
        if (!condition) {
            errorMessages.add(message);
            softAssert.fail(message);
        }
    }

    public List<String> getErrorMessages() {
        return new ArrayList<>(errorMessages);
    }

    public void assertAll() {
        if (!errorMessages.isEmpty()) {
            softAssert.assertAll();
            throw new AssertionError("Soft assertion errors: " + String.join("; ", errorMessages));
        }
    }

    public void reset() {
        errorMessages.clear();
        softAssert = new SoftAssert();
    }
}
