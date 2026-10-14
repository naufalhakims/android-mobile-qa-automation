# CI Regression and Reporting

## GitHub Actions workflow

`.github/workflows/android-mobile-qa.yml` runs on pushes, pull requests, and manual dispatch. It provisions Java 17 and Node.js, starts an Android API 30 Google APIs x86_64 emulator, installs the bundled app APK, installs Appium with UiAutomator2, then runs the full Maven test suite.

The job uploads these artifacts even after a failed run (14-day retention):

- JUnit/Surefire reports from `target/surefire-reports/`
- Failure screenshots, UI XML, and messages from `target/failure-artifacts/`
- Appium server logs from `target/`

The Appium server process is cleaned up when the emulator step exits, including on test failure.

## Local reporting

Run the full regression suite with `mvn test`. Maven writes machine-readable XML and text summaries to `target/surefire-reports/`. `FailureArtifactExtension` captures the active screen and UI hierarchy when a test method throws while its Appium session remains usable.

## Current status

Workflow configuration is present, but it has not yet been exercised by a hosted GitHub Actions run. The most recent local consolidated negative run failed during UiAutomator2 session startup; the prior full-suite attempt also hit a local memory/pagefile allocation failure. See [QA Test Report](qa-test-report.md) and [Negative Testing Report](negative-testing-report.md) for test-level outcomes and limitations.
