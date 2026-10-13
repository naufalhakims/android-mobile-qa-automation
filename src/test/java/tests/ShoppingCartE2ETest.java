package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import pages.CartPage;
import pages.ProductDetailPage;
import pages.ProductPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ShoppingCartE2ETest extends BaseTest {

    @Test
    void shouldAddBackpackToCartAndVerifyDetails() {

        // Step 1: Open the product.
        ProductPage productPage = new ProductPage(driver);

        assertTrue(
                productPage.isProductsScreenDisplayed(),
                "Products screen should be displayed"
        );

        productPage.openProduct("Sauce Labs Backpack");

        // Step 2: Verify the product details.
        ProductDetailPage detailPage =
                new ProductDetailPage(driver);

        detailPage.waitUntilLoaded();

        assertTrue(
                detailPage.isProductNameDisplayed(),
                "Backpack name should be displayed"
        );

        assertTrue(
                detailPage.isProductPriceDisplayed(),
                "Backpack price should be displayed"
        );

        // Step 3: Add the product to the cart.
        detailPage.addToCart();

        // Step 4: Verify the cart badge.
        CartPage cartPage = new CartPage(driver);

        assertTrue(
                cartPage.isCartBadgeOne(),
                "Cart badge should display 1"
        );

        // Step 5: Open the cart.
        cartPage.openCart();
        cartPage.waitUntilLoaded();

        assertTrue(
                cartPage.isCartScreenDisplayed(),
                "Cart screen should be displayed"
        );

        // Step 6: Verify the cart contents.
        assertTrue(
                cartPage.isBackpackDisplayed(),
                "Backpack should be present in the cart"
        );

        assertTrue(
                cartPage.isBackpackPriceDisplayed(),
                "Backpack price should be displayed in the cart"
        );
    }
}
