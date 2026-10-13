package tests;

import base.BaseTest;
import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ProductPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
class ProductDetailTest extends BaseTest {

    @Test
    void shouldDisplayCorrectProductDetails() {
        ProductPage productPage = new ProductPage(driver);
        productPage.openProduct("Sauce Labs Backpack");

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(10)
        );

        By productName = By.xpath(
                "//*[@text='Sauce Labs Backpack']"
        );

        By productPrice = By.xpath(
                "//*[@text='$29.99']"
        );

        wait.until(d ->
                !d.findElements(productName).isEmpty()
                        && !d.findElements(productPrice).isEmpty()
        );

        assertTrue(
                driver.findElement(productName).isDisplayed(),
                "Product name should be displayed"
        );

        assertTrue(
                driver.findElement(productPrice).isDisplayed(),
                "Product price should be displayed"
        );
    }
}
