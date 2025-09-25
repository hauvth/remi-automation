package element;

import config.TestConfig;
import driver.DriverManager;
import helper.DriverHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class MobElement implements ExpectedCondition<Boolean> {

    private String dynamicLocator;
    private By locator;
    public MobElement (By locator){
        this.locator = locator;
    }
    public MobElement (String dynamicLocator){
        this.dynamicLocator = dynamicLocator;
    }



    public <T> T doAction(WebElement element, Supplier<T> action) {
        try {
            WebDriverWait wait = DriverHelper.getWait(TestConfig.get().getTimeout());
            wait.until(ExpectedConditions.visibilityOf(element));
            return action.get();
        } catch (TimeoutException e) {
            LogUtils.error("Element not visible within " + TestConfig.get().getTimeout() + " seconds: " + element.toString());
            throw new TimeoutException("Element not visible within " + TestConfig.get().getTimeout() + " seconds: " + element.toString(), e);
        }
    }

    @Override
    public Boolean apply(WebDriver driver) {
        try {
            assert driver != null;
            WebElement element = driver.findElement(locator);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    public void createDynamicLocator(String... values){
        String locator = String.format(this.dynamicLocator, (Object[]) values);
        this.locator = By.xpath(locator);
    }
    public AppiumDriver getAppiumDriver(){
        return DriverManager.getDriver();
    }

    public void click(){
        getAppiumDriver().findElement(locator).click();
    }

    public void sendKeys(String text){
        getAppiumDriver().findElement(locator).sendKeys(text);
    }
    public String getText(){
        return getAppiumDriver().findElement(locator).getText();
    }
    public boolean isDisplayed(){
        return getAppiumDriver().findElement(locator).isDisplayed();
    }
    public void clear(){
        getAppiumDriver().findElement(locator).clear();
    }

    public List<MobElement> findElements(){
        List<MobElement> elements = new ArrayList<>();
        getAppiumDriver().findElements(locator).
                forEach(element -> elements.add(new MobElement(By.xpath(element.toString()))));
        return elements;
    }

    public List<WebElement> getWebElements(){
        return getAppiumDriver().findElements(locator);
    }


}
