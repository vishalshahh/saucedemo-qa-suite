package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class InventoryPage {
    WebDriver driver;

    // Locators
    By inventoryItems = By.className("inventory_item");
    By itemNames = By.className("inventory_item_name");
    By itemPrices = By.className("inventory_item_price");
    By addToCartButtons = By.cssSelector(".btn_inventory");
    By cartIcon = By.className("shopping_cart_link");
    By cartBadge = By.className("shopping_cart_badge");
    By sortDropdown = By.className("product_sort_container");
    By pageTitle = By.className("title");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isOnInventoryPage() {
        return driver.getCurrentUrl().contains("inventory");
    }

    public String getPageTitle() {
        return driver.findElement(pageTitle).getText();
    }

    public int getInventoryItemCount() {
        return driver.findElements(inventoryItems).size();
    }

    public void addFirstItemToCart() {
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
        }
    }

    public void addItemToCartByName(String itemName) {
        List<WebElement> names = driver.findElements(itemNames);
        for (WebElement name : names) {
            if (name.getText().equalsIgnoreCase(itemName)) {
                // Find the parent item and click its add to cart button
                WebElement parent = name.findElement(
                        By.xpath("ancestor::div[@class='inventory_item']")
                );
                parent.findElement(By.cssSelector(".btn_inventory")).click();
                return;
            }
        }
    }

    public String getCartBadgeCount() {
        try {
            return driver.findElement(cartBadge).getText();
        } catch (Exception e) {
            return "0";
        }
    }

    public void goToCart() {
        driver.findElement(cartIcon).click();
    }

    public List<String> getAllItemNames() {
        List<WebElement> nameElements = driver.findElements(itemNames);
        return nameElements.stream()
                .map(WebElement::getText)
                .toList();
    }

    public List<String> getAllItemPrices() {
        List<WebElement> priceElements = driver.findElements(itemPrices);
        return priceElements.stream()
                .map(WebElement::getText)
                .toList();
    }

    public void sortItemsBy(String option) {
        // Options: "az", "za", "lohi", "hilo"
        WebElement dropdown = driver.findElement(sortDropdown);
        new org.openqa.selenium.support.ui.Select(dropdown)
                .selectByValue(option);
    }
}