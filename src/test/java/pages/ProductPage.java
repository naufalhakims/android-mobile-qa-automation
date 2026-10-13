
package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductPage {

    private final AndroidDriver driver;

    private final By screenTitle =
            By.xpath("//*[@content-desc='products screen']");

    private final By productNames =
            AppiumBy.accessibilityId("store item text");

    private final By productPrices =
            AppiumBy.accessibilityId("store item price");

    public ProductPage(AndroidDriver driver) {
        this.driver = driver;
    }


    public boolean isProductsScreenDisplayed() {
        return !driver.findElements(
                AppiumBy.accessibilityId("products screen")
        ).isEmpty();
    }

    public List<WebElement> getProductNames() {
        return driver.findElements(productNames);
    }

    public List<WebElement> getProductPrices() {
        return driver.findElements(productPrices);
    }

    public void openProduct(String productName) {
        driver.findElement(
                By.xpath("//*[@content-desc='store item text' and @text='"
                        + productName + "']")
        ).click();
    }
}