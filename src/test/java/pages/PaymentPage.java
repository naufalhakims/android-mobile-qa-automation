package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PaymentPage {
    private final AndroidDriver driver;
    private final By paymentScreen = AppiumBy.accessibilityId("review order screen");
    private final By cardholderField = AppiumBy.accessibilityId("Full Name* input field");
    private final By cardNumberField = AppiumBy.accessibilityId("Card Number* input field");
    private final By expirationField = AppiumBy.accessibilityId("Expiration Date* input field");
    private final By securityCodeField = AppiumBy.accessibilityId("Security Code* input field");
    private final By reviewOrderButton = AppiumBy.accessibilityId("Review Order button");

    public PaymentPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public void waitUntilLoaded() {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                d -> !d.findElements(paymentScreen).isEmpty()
        );
    }

    public void enterTestPaymentAndReviewOrder() {
        enter(cardholderField, "QA Test User");
        enter(cardNumberField, "4111111111111111");
        enter(expirationField, "12/30");
        enter(securityCodeField, "123");
        try {
            driver.hideKeyboard();
        } catch (org.openqa.selenium.WebDriverException ignored) {
            // The keyboard may already be hidden after the final field.
        }
        driver.findElement(reviewOrderButton).click();
    }

    public void submitEmptyPaymentForm() {
        driver.findElement(reviewOrderButton).click();
    }

    public String getPaymentValidationError(String fieldLabel) {
        By error = By.xpath("//*[@content-desc='" + fieldLabel
                + "-error-message']/*[@text]");
        var text = driver.findElements(error);
        return text.isEmpty() ? "" : text.get(0).getText();
    }

    public boolean hasLeftPaymentScreen() {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                    d -> d.findElements(paymentScreen).isEmpty()
            );
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    private void enter(By field, String value) {
        var input = driver.findElement(field);
        input.clear();
        input.sendKeys(value);
    }
}
