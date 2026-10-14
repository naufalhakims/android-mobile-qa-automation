package base;

import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FailureArtifactExtension implements TestExecutionExceptionHandler {
    @Override
    public void handleTestExecutionException(
            ExtensionContext context,
            Throwable failure
    ) throws Throwable {
        Object instance = context.getRequiredTestInstance();
        if (instance instanceof BaseTest baseTest && baseTest.driver != null) {
            captureArtifacts(baseTest.driver, context, failure);
        }
        throw failure;
    }

    private void captureArtifacts(
            AndroidDriver driver,
            ExtensionContext context,
            Throwable failure
    ) {
        String timestamp = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS")
        );
        String testId = (context.getRequiredTestClass().getSimpleName()
                + "-" + context.getRequiredTestMethod().getName())
                .replaceAll("[^A-Za-z0-9._-]", "_");
        Path outputDir = Path.of("target", "failure-artifacts");
        String baseName = testId + "-" + timestamp;

        try {
            Files.createDirectories(outputDir);
            byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);
            Files.write(outputDir.resolve(baseName + ".png"), screenshot);
            Files.writeString(outputDir.resolve(baseName + ".xml"), driver.getPageSource());
            Files.writeString(outputDir.resolve(baseName + ".txt"),
                    failure.getClass().getName() + ": " + failure.getMessage());
            System.err.println("Failure artifacts saved to "
                    + outputDir.resolve(baseName));
        } catch (Exception artifactFailure) {
            System.err.println("Could not capture failure artifacts: "
                    + artifactFailure.getMessage());
        }
    }
}
