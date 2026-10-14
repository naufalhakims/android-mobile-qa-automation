# QA Test Report

**Execution date:** 2026-10-14 (Asia/Jakarta)  
**Application:** Sauce Labs My Demo App, Android APK `1.0.0.build-130`  
**Automation:** Java 17, Maven, JUnit 5, Appium Java Client, UiAutomator2

## Coverage

- Positive checkout E2E: product details, cart, standard demo-user login, shipping, test payment, order review, and order confirmation.
- Negative checkout: the app-listed locked-out user, empty shipping form, and empty payment form.
- Existing product browsing and cart regression tests remain part of the Maven suite.

## Execution status

- The positive checkout E2E previously passed locally through order confirmation (1 test, 0 failures, 0 errors).
- Negative scenarios were individually exercised earlier: locked-out login and empty shipping/payment validation passed, and failure screenshots were produced during exploratory assertions. The final consolidated regression method could not be re-executed on 2026-10-14 because UiAutomator2 instrumentation failed to initialize while creating the Appium session (30-second timeout in `BaseTest.setUp`). This happened before a test method started, so no screenshot can be captured for that startup failure.
- The latest full-suite attempt did not complete because the local JVM could not allocate enough native/pagefile memory. It is not counted as a passing regression run.
- The project is configured for local test execution only; CI is not included.

## Artifacts

Surefire XML/text reports are written to `target/surefire-reports/`. Failure screenshots (`.png`), UI hierarchy (`.xml`), and exception messages (`.txt`) are saved under `target/failure-artifacts/` when a test body fails while a driver session is available.

## Re-run

With an Android emulator/device and an Appium server available at the configured endpoint, run:

```powershell
mvn test
```

For checkout-only verification:

```powershell
mvn -Dtest=CheckoutE2ETest,CheckoutNegativeTest test
```
