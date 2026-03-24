package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {
    WebDriver driver;

    // Locators
    By cartItems = By.className("cart_item");
    By cartItemNames = By.className("inventory_item_name");
    By cartItemPrices = By.className("inventory_item_price");
    By removeButtons = By.cssSelector("[data-test^='remove']");
    By checkoutButton = By.id("checkout");
    By continueShoppingButton = By.id("continue-shopping");
    By cartQuantity = By.className("cart_quantity");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isOnCartPage() {
        return driver.getCurrentUrl().contains("cart") ||
                driver.getCurrentUrl().contains("basket");
    }

    public int getCartItemCount() {
        return driver.findElements(cartItems).size();
    }

    public List<String> getCartItemNames() {
        List<WebElement> nameElements = driver.findElements(cartItemNames);
        return nameElements.stream()
                .map(WebElement::getText)
                .toList();
    }

    public void clickCheckout() {
        dismissAnyDialog(); // dismiss password popup if present
        driver.findElement(checkoutButton).click();
    }

    public void removeFirstItem() {
        dismissAnyDialog(); // dismiss password popup if present
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> buttons = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(removeButtons)
        );
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
        }
    }

    public void clickContinueShopping() {
        driver.findElement(continueShoppingButton).click();
    }

    public boolean isItemInCart(String itemName) {
        return getCartItemNames().contains(itemName);
    }


    private void dismissAnyDialog() {
        try {
            org.openqa.selenium.Alert alert = driver.switchTo().alert();
            alert.dismiss();
        } catch (Exception e) {
            // No alert present — continue normally
        }
    }

}