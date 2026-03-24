package stepDefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;
import utils.DriverManager;

public class CartSteps {

    WebDriver driver;
    LoginPage loginPage;
    InventoryPage inventoryPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;

    @Before
    public void setUp() {
        driver       = DriverManager.getDriver();
        loginPage    = new LoginPage(driver);
        inventoryPage= new InventoryPage(driver);
        cartPage     = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @Given("I am logged in as {string}")
    public void iAmLoggedInAs(String username) {
        loginPage.navigateTo();
        loginPage.loginWith(username, "secret_sauce");
    }

    @When("I add the first item to cart")
    public void iAddTheFirstItemToCart() {
        inventoryPage.addFirstItemToCart();
    }

    @When("I add {string} to cart")
    public void iAddItemToCart(String itemName) {
        inventoryPage.addItemToCartByName(itemName);
    }

    @When("I go to the cart page")
    public void iGoToTheCartPage() {
        inventoryPage.goToCart();
    }

    @When("I remove the first item from cart")
    public void iRemoveFirstItemFromCart() {
        cartPage.removeFirstItem();
    }

    @When("I proceed to checkout")
    public void iProceedToCheckout() {
        cartPage.clickCheckout();
    }

    @When("I fill checkout info {string} {string} {string}")
    public void iFillCheckoutInfo(String first, String last, String zip) {
        checkoutPage.enterCheckoutInfo(first, last, zip);
        checkoutPage.clickContinue();
    }

    @When("I complete the order")
    public void iCompleteTheOrder() {
        checkoutPage.clickFinish();
    }

    @Then("the cart badge should show {string}")
    public void theCartBadgeShouldShow(String expected) {
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), expected);
    }

    @Then("{string} should be visible in cart")
    public void itemShouldBeVisibleInCart(String itemName) {
        Assert.assertTrue(cartPage.isItemInCart(itemName),
                itemName + " should be in cart");
    }

    @Then("the cart should be empty")
    public void theCartShouldBeEmpty() {
        Assert.assertEquals(cartPage.getCartItemCount(), 0,
                "Cart should be empty after removal");
    }

    @Then("I should see order success message")
    public void iShouldSeeOrderSuccessMessage() {
        Assert.assertTrue(checkoutPage.isOrderSuccessful(),
                "Order success message should appear");
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}