
package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {
    private final AndroidDriver driver;

    private final By checkoutButton =
            AppiumBy.accessibilityId("Proceed To Checkout button");
    private final By cartScreen =
            AppiumBy.accessibilityId("cart screen");
    private final By addressScreen =
            AppiumBy.accessibilityId("checkout address screen");
    private final By fullNameField =
            AppiumBy.accessibilityId("Full Name* input field");
    private final By addressLine1Field =
            AppiumBy.accessibilityId("Address Line 1* input field");
    private final By cityField =
            AppiumBy.accessibilityId("City* input field");
    private final By zipCodeField =
            AppiumBy.accessibilityId("Zip Code* input field");
    private final By countryField =
            AppiumBy.accessibilityId("Country* input field");
    private final By toPaymentButton =
            AppiumBy.accessibilityId("To Payment button");

    public CheckoutPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public void proceedToCheckout() {
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(d ->
                d.findElement(checkoutButton).isDisplayed()
                        && d.findElement(checkoutButton).isEnabled()
        );

        driver.findElement(checkoutButton).click();
    }

    public boolean hasLeftCartScreen() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            return wait.until(d -> d.findElements(cartScreen).isEmpty());
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public void waitUntilAddressScreenLoaded() {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                d -> !d.findElements(addressScreen).isEmpty()
        );
    }

    public boolean isAddressFormDisplayed() {
        return !driver.findElements(addressScreen).isEmpty()
                && !driver.findElements(fullNameField).isEmpty()
                && !driver.findElements(toPaymentButton).isEmpty();
    }

    public void submitEmptyAddressForm() {
        driver.findElement(toPaymentButton).click();
    }

    public String getAddressValidationError(String fieldLabel) {
        return getValidationMessage(fieldLabel + "-error-message");
    }

    private String getValidationMessage(String accessibilityId) {
        By message = By.xpath("//*[@content-desc='" + accessibilityId + "']/*[@text]");
        var text = driver.findElements(message);
        return text.isEmpty() ? "" : text.get(0).getText();
    }

    public void continueToPayment() {
        enter(fullNameField, "QA Test User");
        enter(addressLine1Field, "123 Test Street");
        enter(cityField, "San Francisco");
        enter(zipCodeField, "94105");
        enter(countryField, "United States");
        try {
            driver.hideKeyboard();
        } catch (org.openqa.selenium.WebDriverException ignored) {
            // The keyboard may already be hidden after the final field.
        }
        driver.findElement(toPaymentButton).click();
    }

    private void enter(By field, String value) {
        var input = driver.findElement(field);
        input.clear();
        input.sendKeys(value);
    }

    public boolean hasLeftAddressScreen() {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                    d -> d.findElements(addressScreen).isEmpty()
            );
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }
}
