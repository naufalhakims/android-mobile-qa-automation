package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final AndroidDriver driver;
    private final By loginScreen = AppiumBy.accessibilityId("login screen");
    private final By standardUser = AppiumBy.accessibilityId("bob@example.com-autofill");
    private final By lockedOutUser = AppiumBy.accessibilityId("alice@example.com (locked out)-autofill");
    private final By loginButton = AppiumBy.accessibilityId("Login button");
    private final By genericError = AppiumBy.accessibilityId("generic-error-message");

    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public void waitUntilLoaded() {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                d -> !d.findElements(loginScreen).isEmpty()
        );
    }

    public void loginAsStandardUser() {
        driver.findElement(standardUser).click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                d -> d.findElement(loginButton).isEnabled()
        );
        driver.findElement(loginButton).click();
    }

    public boolean hasLeftLoginScreen() {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                    d -> d.findElements(loginScreen).isEmpty()
            );
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public String loginAsLockedOutUser() {
        driver.findElement(lockedOutUser).click();
        driver.findElement(loginButton).click();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                d -> !d.findElements(genericError).isEmpty()
        );
        var errorText = driver.findElements(By.xpath(
                "//*[@content-desc='generic-error-message']/*[@text]"));
        return errorText.isEmpty() ? "" : errorText.get(0).getText();
    }
}
