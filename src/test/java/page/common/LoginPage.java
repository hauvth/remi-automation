package page.common;

public interface LoginPage {

    abstract void enterUsername(String user);
    abstract void enterPassword(String pass);
    abstract void clickLogin();
}
