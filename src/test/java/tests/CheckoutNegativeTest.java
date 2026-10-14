package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.PaymentPage;
import pages.ProductDetailPage;
import pages.ProductPage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckoutNegativeTest extends BaseTest {

    @Test
    void shouldShowSupportedErrorsForInvalidAndEmptyInputs() {
        LoginPage loginPage = openLoginForm();
        String error = loginPage.loginAsLockedOutUser();
        assertEquals("Sorry, this user has been locked out.", error,
                "The app-listed locked-out account should show its supported error");
        assertTrue(!loginPage.hasLeftLoginScreen(),
                "A locked-out account should remain on the login screen");

        loginPage.loginAsStandardUser();
        assertTrue(loginPage.hasLeftLoginScreen(),
                "A valid demo user should clear the login error and proceed");
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.waitUntilAddressScreenLoaded();
        checkoutPage.submitEmptyAddressForm();

        assertEquals("Please provide your full name.",
                checkoutPage.getAddressValidationError("Full Name*"));
        assertEquals("Please provide your address.",
                checkoutPage.getAddressValidationError("Address Line 1*"));
        assertEquals("Please provide your city.",
                checkoutPage.getAddressValidationError("City*"));
        assertEquals("Please provide your zip code.",
                checkoutPage.getAddressValidationError("Zip Code*"));
        assertEquals("Please provide your country.",
                checkoutPage.getAddressValidationError("Country*"));
        assertTrue(checkoutPage.isAddressFormDisplayed(),
                "Submitting an empty address should keep the user on the address form");

        checkoutPage.continueToPayment();

        PaymentPage paymentPage = new PaymentPage(driver);
        paymentPage.waitUntilLoaded();
        paymentPage.submitEmptyPaymentForm();

        String expirationError = paymentPage.getPaymentValidationError("Expiration Date*");
        String securityCodeError = paymentPage.getPaymentValidationError("Security Code*");

        assertEquals("Value looks invalid.", expirationError,
                "An empty expiration date should show the app's invalid-value error");
        assertEquals("Value looks invalid.", securityCodeError,
                "An empty security code should show the app's invalid-value error");
    }

    private LoginPage openLoginForm() {
        ProductPage productPage = new ProductPage(driver);
        productPage.openProduct("Sauce Labs Backpack");
        ProductDetailPage detailPage = new ProductDetailPage(driver);
        detailPage.waitUntilLoaded();
        detailPage.addToCart();
        CartPage cartPage = new CartPage(driver);
        cartPage.openCart();
        cartPage.waitUntilLoaded();
        new CheckoutPage(driver).proceedToCheckout();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitUntilLoaded();
        return loginPage;
    }
}
