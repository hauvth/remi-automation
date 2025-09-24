package page.android;

import annotation.Platform;
import element.MobElement;
import org.openqa.selenium.By;
import page.BasePage;
import page.common.LoginPage;

@Platform("ANDROID")
public class AndroidLoginPage extends BasePage implements LoginPage {

    MobElement username = new MobElement(By.xpath("//android.widget.EditText[@content-desc='test-Username']"));
    MobElement password = new MobElement(By.xpath("//android.widget.EditText[@content-desc='test-Password']"));
    MobElement loginButton = new MobElement(By.xpath("//android.view.ViewGroup[@content-desc='test-LOGIN']"));
    @Override
    public void enterUsername(String user) {
        username.sendKeys(user);
    }
    @Override
    public void enterPassword(String pass) {
        password.sendKeys(pass);
    }
    @Override
    public void clickLogin() {
        loginButton.click();
    }

}
