package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductDetailPage {

    private final AndroidDriver driver;

    private final By productName =
            By.xpath("//*[@text='Sauce Labs Backpack']");

    private final By productPrice =
            By.xpath("//*[@text='$29.99']");

    private final By addToCartButton =
            AppiumBy.accessibilityId("Add To Cart button");

    public ProductDetailPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public void waitUntilLoaded() {
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(15));

        wait.until(d ->
                !d.findElements(productName).isEmpty()
                        && !d.findElements(productPrice).isEmpty()
        );
    }

    public boolean isProductNameDisplayed() {
        return driver.findElement(productName).isDisplayed();
    }

    public boolean isProductPriceDisplayed() {
        return driver.findElement(productPrice).isDisplayed();
    }

    public void addToCart() {
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(d ->
                d.findElement(addToCartButton).isDisplayed()
                        && d.findElement(addToCartButton).isEnabled()
        );

        driver.findElement(addToCartButton).click();
    }
}
