package stepDefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.LoginPage;
import utils.DriverManager;

public class LoginSteps {
    WebDriver driver;
    LoginPage loginPage;

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        loginPage = new LoginPage(driver);
    }

    @Given("I am on the SauceDemo login page")
    public void iAmOnLoginPage() {
        loginPage.navigateTo();
    }

    @When("I login with username {string} and password {string}")
    public void iLoginWith(String username, String password) {
        loginPage.loginWith(username, password);
    }

    @Then("I should be redirected to the inventory page")
    public void iShouldBeOnInventoryPage() {
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @Then("I should see an error message")
    public void iShouldSeeError() {
        Assert.assertFalse(loginPage.getErrorMessage().isEmpty());
    }

    @Then("I should see a locked out error")
    public void iShouldSeeLockedOutError() {
        Assert.assertTrue(loginPage.getErrorMessage().contains("locked out"));
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}