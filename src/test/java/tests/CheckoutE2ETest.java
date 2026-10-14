package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.OrderReviewPage;
import pages.PaymentPage;
import pages.ProductDetailPage;
import pages.ProductPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckoutE2ETest extends BaseTest {

    @Test
    void shouldCompleteCheckoutFromAppLaunch() {
        ProductPage productPage = new ProductPage(driver);
        assertTrue(productPage.isProductsScreenDisplayed(),
                "App should launch to the products screen");

        productPage.openProduct("Sauce Labs Backpack");
        ProductDetailPage detailPage = new ProductDetailPage(driver);
        detailPage.waitUntilLoaded();
        detailPage.addToCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.openCart();
        cartPage.waitUntilLoaded();
        assertTrue(cartPage.isBackpackDisplayed(),
                "Cart should contain Sauce Labs Backpack");

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.proceedToCheckout();
        assertTrue(checkoutPage.hasLeftCartScreen(),
                "Proceed To Checkout should navigate away from the cart");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitUntilLoaded();
        loginPage.loginAsStandardUser();
        assertTrue(loginPage.hasLeftLoginScreen(),
                "The standard demo user should proceed past login");

        checkoutPage.waitUntilAddressScreenLoaded();
        assertTrue(checkoutPage.isAddressFormDisplayed(),
                "Checkout address form and To Payment action should be displayed");

        checkoutPage.continueToPayment();
        assertTrue(checkoutPage.hasLeftAddressScreen(),
                "To Payment should advance the checkout flow");

        PaymentPage paymentPage = new PaymentPage(driver);
        paymentPage.waitUntilLoaded();
        paymentPage.enterTestPaymentAndReviewOrder();
        assertTrue(paymentPage.hasLeftPaymentScreen(),
                "Review Order should advance to the order review screen");

        OrderReviewPage reviewPage = new OrderReviewPage(driver);
        reviewPage.waitUntilLoaded();
        assertTrue(reviewPage.showsExpectedOrder(),
                "Order review should show the backpack, total, and Place Order action");

        reviewPage.placeOrder();
        reviewPage.waitUntilComplete();
        assertTrue(reviewPage.showsOrderConfirmation(),
                "Placing the demo order should show the order confirmation screen");
    }
}
