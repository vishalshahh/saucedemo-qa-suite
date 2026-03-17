Feature: Shopping Cart Functionality

  Background:
    Given I am logged in as "standard_user"

  Scenario: Add single item to cart
    When I add the first item to cart
    Then the cart badge should show "1"

  Scenario: Add specific item by name
    When I add "Sauce Labs Backpack" to cart
    And I go to the cart page
    Then "Sauce Labs Backpack" should be visible in cart

  Scenario: Remove item from cart
    When I add the first item to cart
    And I go to the cart page
    And I remove the first item from cart
    Then the cart should be empty

  Scenario: Complete full checkout
    When I add the first item to cart
    And I go to the cart page
    And I proceed to checkout
    And I fill checkout info "Vishal" "Shah" "201001"
    And I complete the order
    Then I should see order success message



## 📊 Final Test Count for Resume

| Suite | Tests | What It Covers |
|---|---|---|
| LoginTest | 3 tests | Valid login, invalid login, locked user |
| CartTest | 8 tests | Inventory, cart, checkout, full E2E flow |
| UserApiTest | 4 tests | GET, POST, DELETE, 404 handling |
| Cucumber | 2 feature files | BDD scenarios for login + cart |
| **Total** | **15 tests** | **Full coverage** |
