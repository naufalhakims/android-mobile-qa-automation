package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {

    private final AndroidDriver driver;

    private final By cartButton =
            AppiumBy.accessibilityId("cart badge");

    private final By cartScreen =
            AppiumBy.accessibilityId("cart screen");

    private final By backpackName =
            By.xpath("//*[@text='Sauce Labs Backpack']");

    private final By backpackPrice =
            By.xpath("//*[@text='$29.99']");

    private final By cartBadgeCountOne =
            By.xpath("//*[@content-desc='cart badge']//*[@text='1']");

    public CartPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public void openCart() {
        driver.findElement(cartButton).click();
    }

    public void waitUntilLoaded() {
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        wait.until(d ->
                !d.findElements(cartScreen).isEmpty()
        );
    }

    public boolean isCartScreenDisplayed() {
        return !driver.findElements(cartScreen).isEmpty();
    }

    public boolean isBackpackDisplayed() {
        return !driver.findElements(backpackName).isEmpty();
    }

    public boolean isBackpackPriceDisplayed() {
        return !driver.findElements(backpackPrice).isEmpty();
    }

    public boolean isCartBadgeOne() {
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            return wait.until(d -> {
                var badge = d.findElements(
                        AppiumBy.accessibilityId("cart badge")
                );

                if (badge.isEmpty()) {
                    return false;
                }

                String pageSource = d.getPageSource();

                return pageSource.contains("content-desc=\"cart badge\"")
                        && pageSource.contains("text=\"1\"");
            });
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println(
                    "Cart badge did not show count 1 within 10 seconds."
            );
            System.out.println(driver.getPageSource());
            return false;
        }
    }

}
