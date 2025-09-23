package element;

import driver.DriverManager;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class MobElement {

    private String dynamicLocator;
    private By locator;
    public MobElement (By locator){
        this.locator = locator;
    }
    public MobElement (String dynamicLocator){
        this.dynamicLocator = dynamicLocator;
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
