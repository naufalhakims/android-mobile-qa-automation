package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderReviewPage {
    private final AndroidDriver driver;
    private final By reviewScreen = AppiumBy.accessibilityId("checkout review order screen");
    private final By backpack = By.xpath("//*[@text='Sauce Labs Backpack']");
    private final By placeOrderButton = AppiumBy.accessibilityId("Place Order button");
    private final By completeScreen = AppiumBy.accessibilityId("checkout complete screen");

    public OrderReviewPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public void waitUntilLoaded() {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                d -> !d.findElements(reviewScreen).isEmpty()
        );
    }

    public boolean showsExpectedOrder() {
        return !driver.findElements(backpack).isEmpty()
                && !driver.findElements(placeOrderButton).isEmpty()
                && driver.getPageSource().contains("$35.98");
    }

    public void placeOrder() {
        driver.findElement(placeOrderButton).click();
    }

    public void waitUntilComplete() {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                d -> !d.findElements(completeScreen).isEmpty()
        );
    }

    public boolean showsOrderConfirmation() {
        return !driver.findElements(completeScreen).isEmpty();
    }
}
