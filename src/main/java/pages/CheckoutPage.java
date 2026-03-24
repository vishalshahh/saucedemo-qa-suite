package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutPage {
    WebDriver driver;
    WebDriverWait wait;

    By firstNameField  = By.cssSelector("[data-test='firstName']");
    By lastNameField   = By.cssSelector("[data-test='lastName']");
    By postalCodeField = By.cssSelector("[data-test='postalCode']");
    By continueButton  = By.cssSelector("[data-test='continue']");
    By finishButton    = By.cssSelector("[data-test='finish']");
    By errorMessage    = By.cssSelector("[data-test='error']");
    By summaryTotal    = By.cssSelector("[data-test='total-label']");
    By successMessage  = By.className("complete-header");


    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterCheckoutInfo(String firstName,
                                  String lastName,
                                  String postalCode) {
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(firstNameField));
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(postalCodeField).sendKeys(postalCode);
    }

    public void clickContinue() {
        wait.until(ExpectedConditions
                .elementToBeClickable(continueButton));
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