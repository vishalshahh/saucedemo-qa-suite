package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutPage {
    WebDriver driver;
    WebDriverWait wait;

    By firstNameField = By.id("first-name");
    By lastNameField  = By.id("last-name");
    By postalCodeField = By.id("postal-code");
    By continueButton  = By.id("continue");
    By finishButton    = By.id("finish");
    By successMessage  = By.className("complete-header");
    By errorMessage    = By.cssSelector("[data-test='error']");

    // ✅ Fixed locator — works on current SauceDemo
    By summaryTotal = By.cssSelector("[data-test='total-label']");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterCheckoutInfo(String firstName,
                                  String lastName,
                                  String postalCode) {
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(postalCodeField).sendKeys(postalCode);
    }

    public void clickContinue() {
        driver.findElement(continueButton).click();
    }

    public void clickFinish() {
        driver.findElement(finishButton).click();
    }

    public String getSuccessMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
        return driver.findElement(successMessage).getText();
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    public String getOrderTotal() {
        // ✅ Wait for element to appear before reading it
        wait.until(ExpectedConditions.visibilityOfElementLocated(summaryTotal));
        return driver.findElement(summaryTotal).getText();
    }

    public boolean isOrderSuccessful() {
        try {
            return getSuccessMessage().contains("Thank you for your order");
        } catch (Exception e) {
            return false;
        }
    }
}