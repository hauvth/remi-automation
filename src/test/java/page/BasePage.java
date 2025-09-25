package page;

import element.MobElement;
import org.openqa.selenium.By;

public abstract class BasePage {

    private MobElement menuButton = new MobElement(By.xpath("//android.view.ViewGroup[@content-desc='test-Menu']" +
            "/android.view.ViewGroup/android.widget.ImageView"));
    private MobElement title = new MobElement(By.xpath("//android.widget.TextView[@text='PRODUCTS']"));

    public void clickMenuButton() {
       menuButton.click();
    }

    public void verifyHomePageDisplayed() {
        title.isDisplayed();
    }
}
