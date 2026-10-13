
package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;

class InspectHomeScreenTest {

    private AndroidDriver driver;

    @BeforeEach
    void setUp() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName("emulator-5554")
                .setAppPackage("com.saucelabs.mydemoapp.rn")
                .setAppActivity("com.saucelabs.mydemoapp.rn.MainActivity")
                .setNoReset(true);

        driver = new AndroidDriver(
                new URL("http://127.0.0.1:4723"),
                options
        );
    }

    @Test
    void shouldInspectHomeScreenElements() {
        var elements = driver.findElements(
                org.openqa.selenium.By.xpath(
                        "//*[@text or @content-desc or @resource-id]"
                )
        );

        System.out.println("===== IMPORTANT UI ELEMENTS =====");

        for (var element : elements) {
            String text = element.getText();
            String contentDesc = element.getAttribute("content-desc");
            String resourceId = element.getAttribute("resource-id");

            if ((text != null && !text.isBlank())
                    || (contentDesc != null && !contentDesc.isBlank())
                    || (resourceId != null && !resourceId.isBlank())) {

                System.out.printf(
                        "text='%s' | description='%s' | resourceId='%s'%n",
                        text, contentDesc, resourceId
                );
            }
        }

        System.out.println("===== END IMPORTANT UI ELEMENTS =====");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}