package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;
import utils.DriverManager;

import java.time.Duration;

public class CartTest {
    WebDriver driver;
    LoginPage loginPage;
    InventoryPage inventoryPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;

    @BeforeMethod
    public void setUp() {
        driver = DriverManager.getDriver();
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);

        // Login before every test
        loginPage.navigateTo();
        loginPage.loginWith("standard_user", "secret_sauce");
    }

    // ─── Inventory Tests ────────────────────────────────────

    @Test(description = "Inventory page should show 6 products")
    public void testInventoryItemCount() {
        Assert.assertEquals(
                inventoryPage.getInventoryItemCount(), 6,
                "Inventory should display exactly 6 items"
        );
    }

    @Test(description = "Page title should be Products")
    public void testInventoryPageTitle() {
        Assert.assertEquals(
                inventoryPage.getPageTitle(), "Products",
                "Page title should be Products"
        );
    }

    @Test(description = "Sort by price low to high should work")
    public void testSortByPriceLowToHigh() {
        inventoryPage.sortItemsBy("lohi");
        java.util.List<String> prices = inventoryPage.getAllItemPrices();

        // Convert price strings to doubles and verify ascending order
        double previousPrice = 0;
        for (String price : prices) {
            double currentPrice = Double.parseDouble(
                    price.replace("$", "")
            );
            Assert.assertTrue(
                    currentPrice >= previousPrice,
                    "Prices should be in ascending order"
            );
            previousPrice = currentPrice;
        }
    }

    // ─── Cart Tests ──────────────────────────────────────────

    @Test(description = "Adding item should update cart badge to 1")
    public void testAddItemToCart() {
        inventoryPage.addFirstItemToCart();
        Assert.assertEquals(
                inventoryPage.getCartBadgeCount(), "1",
                "Cart badge should show 1 after adding item"
        );
    }

    @Test(description = "Added item should appear in cart page")
    public void testItemAppearsInCart() {
        String targetItem = "Sauce Labs Backpack";
        inventoryPage.addItemToCartByName(targetItem);
        inventoryPage.goToCart();

        // Add explicit wait for cart page to fully load
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("cart"));

        Assert.assertTrue(
                cartPage.isOnCartPage(),
                "Should be on cart page"
        );
        Assert.assertTrue(
                cartPage.isItemInCart(targetItem),
                targetItem + " should be in cart"
        );
    }

    @Test(description = "Cart should show correct item count")
    public void testCartItemCount() {
        // Add 2 items
        inventoryPage.addFirstItemToCart();
        java.util.List<String> allItems = inventoryPage.getAllItemNames();
        if (allItems.size() > 1) {
            inventoryPage.addItemToCartByName(allItems.get(1));
        }

        inventoryPage.goToCart();
        Assert.assertEquals(
                cartPage.getCartItemCount(), 2,
                "Cart should contain 2 items"
        );
    }

    @Test(description = "Removing item from cart should update count")
    public void testRemoveItemFromCart() {
        inventoryPage.addFirstItemToCart();
        inventoryPage.goToCart();

        int beforeCount = cartPage.getCartItemCount();
        cartPage.removeFirstItem();
        int afterCount = cartPage.getCartItemCount();

        Assert.assertEquals(
                afterCount, beforeCount - 1,
                "Item count should decrease by 1 after removal"
        );
    }

    @Test(description = "Continue shopping should go back to inventory")
    public void testContinueShopping() {
        inventoryPage.goToCart();
        cartPage.clickContinueShopping();

        Assert.assertTrue(
                inventoryPage.isOnInventoryPage(),
                "Should return to inventory page"
        );
    }

    // ─── Checkout Tests ──────────────────────────────────────

    @Test(description = "Complete checkout flow should show success")
    public void testCompleteCheckoutFlow() {
        // Add item
        inventoryPage.addFirstItemToCart();
        inventoryPage.goToCart();

        // Proceed to checkout
        cartPage.clickCheckout();

        // Fill checkout info
        checkoutPage.enterCheckoutInfo(
                "Vishal", "Shah", "201001"
        );
        checkoutPage.clickContinue();

        // Verify order summary shows total
        String total = checkoutPage.getOrderTotal();
        Assert.assertNotNull(total, "Order total should be visible");
        Assert.assertTrue(
                total.contains("Total"),
                "Total label should appear on summary"
        );

        // Complete order
        checkoutPage.clickFinish();

        Assert.assertTrue(
                checkoutPage.isOrderSuccessful(),
                "Order completion message should appear"
        );
    }

    @Test(description = "Checkout without filling info should show error")
    public void testCheckoutWithoutInfo() {
        inventoryPage.addFirstItemToCart();
        inventoryPage.goToCart();
        cartPage.clickCheckout();

        // Click continue without filling info
        checkoutPage.clickContinue();

        String error = checkoutPage.getErrorMessage();
        Assert.assertFalse(
                error.isEmpty(),
                "Error message should appear when info is missing"
        );
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}