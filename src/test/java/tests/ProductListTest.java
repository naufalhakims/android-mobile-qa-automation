
package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import pages.ProductPage;

import static org.junit.jupiter.api.Assertions.*;

class ProductListTest extends BaseTest {

    @Test
    void shouldDisplayProductList() {
        ProductPage productPage = new ProductPage(driver);

        assertTrue(
                productPage.isProductsScreenDisplayed(),
                "Products screen should be displayed"
        );

        var names = productPage.getProductNames();
        var prices = productPage.getProductPrices();

        assertFalse(names.isEmpty(),
                "Product list should not be empty");

        assertEquals(names.size(), prices.size(),
                "Each product should have a corresponding price");

        assertTrue(
                names.stream().anyMatch(
                        item -> item.getText().equals("Sauce Labs Backpack")
                ),
                "Product list should contain Sauce Labs Backpack"
        );
    }
}