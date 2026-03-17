package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import utils.DriverManager;

public class LoginTest {
    WebDriver driver;
    LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        driver = DriverManager.getDriver();
        loginPage = new LoginPage(driver);
        loginPage.navigateTo();
    }

    @Test(description = "Valid login should redirect to inventory page")
    public void testValidLogin() {
        loginPage.loginWith("standard_user", "secret_sauce");
        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory"),
                "Should be on inventory page"
        );
    }

    @Test(description = "Invalid login should show error message")
    public void testInvalidLogin() {
        loginPage.loginWith("wrong_user", "wrong_pass");
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(
                error.contains("Username and password do not match"),
                "Error message should appear"
        );
    }

    @Test(description = "Locked out user should see specific error")
    public void testLockedOutUser() {
        loginPage.loginWith("locked_out_user", "secret_sauce");
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(
                error.contains("locked out"),
                "Locked out message should appear"
        );
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}