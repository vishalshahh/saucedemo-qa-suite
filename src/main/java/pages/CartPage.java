package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class CartPage {
    WebDriver driver;

    // Locators
    By cartItems = By.className("cart_item");
    By cartItemNames = By.className("inventory_item_name");
    By cartItemPrices = By.className("inventory_item_price");
    By removeButtons = By.cssSelector(".cart_button");
    By checkoutButton = By.id("checkout");
    By continueShoppingButton = By.id("continue-shopping");
    By cartQuantity = By.className("cart_quantity");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isOnCartPage() {
        return driver.getCurrentUrl().contains("cart");
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

    public void removeFirstItem() {
        List<WebElement> buttons = driver.findElements(removeButtons);
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
        }
    }

    public void clickCheckout() {
        driver.findElement(checkoutButton).click();
    }

    public void clickContinueShopping() {
        driver.findElement(continueShoppingButton).click();
    }

    public boolean isItemInCart(String itemName) {
        return getCartItemNames().contains(itemName);
    }
}