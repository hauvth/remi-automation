package stepDefinitions;

import io.cucumber.java.en.When;
import page.PageFactory;
import page.common.LoginPage;

public class LoginSteps {
    private LoginPage loginPage = PageFactory.getPage(LoginPage.class);

//    @When("I enter valid credentials {string} and {string}")
//    public void loginToHomePage(String username, String password) {
//        loginPage.enterUsername(username);
//        loginPage.enterPassword(password);
//        loginPage.clickLogin();
//    }

    @When("I enter valid credentials standard_user and secret_sauce")
    public void iEnterValidCredentialsStandard_userAndSecret_sauce() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
    }

    @When("I enter valid credentials {string} and {string}")
    public void iEnterValidCredentialsAnd(String userName, String password) {
        loginPage.enterUsername(userName);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }
}
