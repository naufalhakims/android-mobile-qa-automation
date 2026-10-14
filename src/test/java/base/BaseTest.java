package base;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;

@ExtendWith(FailureArtifactExtension.class)
public abstract class BaseTest {

    protected AndroidDriver driver;

    @BeforeEach
    void setUp() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName("emulator-5554")
                .setAppPackage("com.saucelabs.mydemoapp.rn")
                .setAppActivity("com.saucelabs.mydemoapp.rn.MainActivity")
                .setNoReset(false);

        driver = new AndroidDriver(
                new URL("http://127.0.0.1:4723"),
                options
        );

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(15)
        );

        // Tunggu sampai halaman awal aplikasi siap.
        wait.until(d ->
                !d.findElements(
                        AppiumBy.accessibilityId("products screen")
                ).isEmpty()
                        ||
                        !d.findElements(
                                AppiumBy.accessibilityId("product screen")
                        ).isEmpty()
        );

        // Jika berada di halaman detail, kembali ke daftar produk.
        if (driver.findElements(
                AppiumBy.accessibilityId("products screen")
        ).isEmpty()) {
            driver.pressKey(new KeyEvent(AndroidKey.BACK));

            wait.until(d ->
                    !d.findElements(
                            AppiumBy.accessibilityId("products screen")
                    ).isEmpty()
            );
        }
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
