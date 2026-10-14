# Android Mobile QA Automation

Automated UI testing project for an Android shopping application using Java, Appium, JUnit 5, and Maven. This project demonstrates mobile test automation, Page Object Model (POM), UI element identification, and end-to-end shopping flow validation.

## Overview

This project automates selected user interactions in the Sauce Labs My Demo App React Native application running on an Android emulator.

The goal is to practice building a maintainable mobile automation framework and validating critical shopping functionality through repeatable automated tests.

## Tech Stack

* **Programming Language:** Java
* **Mobile Automation:** Appium
* **Automation Driver:** UiAutomator2
* **Test Framework:** JUnit 5
* **Build Tool:** Maven
* **Test Environment:** Android Emulator
* **IDE:** IntelliJ IDEA
* **Version Control:** Git

## Test Coverage

The current test suite covers the following scenarios:

| Test Class              | Description                                                                                                                                  |
| ----------------------- | -------------------------------------------------------------------------------------------------------------------------------------------- |
| `AppLaunchTest`         | Verifies that the application launches and an active Appium session can access the UI.                                                       |
| `InspectHomeScreenTest` | Inspects the product listing screen and its UI elements.                                                                                     |
| `ProductListTest`       | Verifies the product listing and checks product names and prices.                                                                            |
| `ProductDetailTest`     | Verifies the selected product's name and price on the detail screen.                                                                         |
| `ShoppingCartE2ETest`   | Tests the shopping flow by opening a product, verifying its details, adding it to the cart, and validating the cart badge and cart contents. |
| `CheckoutE2ETest`       | Runs from the product screen through cart, demo-user login, shipping and payment forms, and verifies the order confirmation. |
| `CheckoutNegativeTest`  | Verifies the app-listed locked-out login error and the validation messages actually shown for empty shipping and payment forms. |

The end-to-end scenario uses the **Sauce Labs Backpack** product.

## Project Structure

```text
android-mobile-qa-automation/
├── src/
│   └── test/
│       └── java/
│           ├── base/
│           │   └── BaseTest.java
│           ├── pages/
│           │   ├── ProductPage.java
│           │   ├── ProductDetailPage.java
│           │   ├── CartPage.java
│           │   ├── LoginPage.java
│           │   ├── CheckoutPage.java
│           │   ├── PaymentPage.java
│           │   └── OrderReviewPage.java
│           └── tests/
│               ├── AppLaunchTest.java
│               ├── InspectHomeScreenTest.java
│               ├── ProductListTest.java
│               ├── ProductDetailTest.java
│               ├── ShoppingCartE2ETest.java
│               ├── CheckoutE2ETest.java
│               └── CheckoutNegativeTest.java
├── docs/
│   ├── qa-test-report.md
│   └── negative-testing-report.md
├── pom.xml
├── .gitignore
└── README.md
```

## Prerequisites

Install and configure the following tools before running the tests:

* Java Development Kit (JDK 17 or compatible configuration)
* Apache Maven
* Node.js and npm
* Appium Server
* Appium UiAutomator2 driver
* Android Studio and Android SDK
* An Android emulator
* Sauce Labs My Demo App Android APK

Verify the installations:

```powershell
java -version
mvn -version
node --version
appium --version
adb devices
```

Ensure that the Android emulator appears in the ADB device list with the status `device`.

## Environment Setup

### 1. Install Appium

If Appium is not installed, install it using npm:

```powershell
npm install -g appium
```

Install the UiAutomator2 driver:

```powershell
appium driver install uiautomator2
```

### 2. Start the Android Emulator

Launch an Android emulator from Android Studio's Device Manager.

Verify the connection:

```powershell
adb devices
```

The emulator used during development is `emulator-5554`.

### 3. Install the Demo Application

Download the Android APK for Sauce Labs My Demo App and install it on the emulator:

```powershell
adb install "$HOME\Downloads\Android-MyDemoAppRN.1.0.0.build-130.apk"
```

The application package used by the test framework is:

```text
com.saucelabs.mydemoapp.rn
```

The configured launch activity is:

```text
com.saucelabs.mydemoapp.rn.MainActivity
```

The APK is installed on the emulator and is not required to be committed to this repository.

### 4. Start Appium Server

Open a separate PowerShell terminal and run:

```powershell
appium
```

Keep the server running while executing the tests. The framework connects to:

```text
http://127.0.0.1:4723
```

## Running the Tests

Open PowerShell in the project root directory.

### Run All Tests

```powershell
mvn clean test
```

### Run a Specific Test Class

Run the end-to-end shopping cart test:

```powershell
mvn -Dtest=ShoppingCartE2ETest test
```

Run the checkout flow through order confirmation:

```powershell
mvn -Dtest=CheckoutE2ETest test
```

Run checkout negative tests:

```powershell
mvn -Dtest=CheckoutNegativeTest test
```

Run the product listing test:

```powershell
mvn -Dtest=ProductListTest test
```

Run the product detail test:

```powershell
mvn -Dtest=ProductDetailTest test
```

### Test Results

Maven displays the test summary in the terminal. A successful run should report zero failures and zero errors.

Detailed test reports are generated under:

```text
target/surefire-reports/
```

When a test method fails while its Appium session is available, the framework saves a screenshot, UI hierarchy XML, and failure message under:

```text
target/failure-artifacts/
```

This is a local mobile automation project. Start an Android emulator and Appium server, then run the Maven tests from your development environment. Test reports and failure artifacts are saved locally under `target/`.

QA reports are maintained in `docs/`:

* `docs/qa-test-report.md` — latest local regression execution summary.
* `docs/negative-testing-report.md` — negative scenarios and observed app validation messages.

## Framework Design

### BaseTest

`BaseTest` provides shared test setup and teardown. It initializes an Appium Android driver before each test and closes the driver session after execution.

### Page Object Model

Page classes separate UI locators and page interactions from test logic.

* `ProductPage` handles product listing interactions.
* `ProductDetailPage` handles product detail verification and adding a product to the cart.
* `CartPage` handles cart navigation and cart-related verification.
* `LoginPage`, `CheckoutPage`, `PaymentPage`, and `OrderReviewPage` handle the checkout flow and order confirmation.

`FailureArtifactExtension` captures screenshots and UI hierarchy data when a test method fails.

This separation improves readability and makes UI locator maintenance easier.

### Explicit Waits

The framework uses Selenium explicit waits to allow application screens and elements to become available before interacting with them. This helps reduce failures caused by timing differences during UI transitions.

## Current End-to-End Scenario

The checkout E2E test follows this sequence:

1. Verify that the product listing screen is displayed.
2. Open Sauce Labs Backpack and verify its details.
3. Add it to the cart and open the cart screen.
4. Proceed to checkout and sign in with the standard demo user.
5. Fill the shipping and test payment forms, review the order, then verify the order confirmation.

## Limitations and Future Improvements

Potential next improvements include:

* Add quantity update and item removal test cases.
* Improve locator resilience and test data management.
* Expand coverage to additional products and application workflows.

## Notes

This is a personal learning and portfolio project focused on Android mobile UI automation and software quality assurance practices.

The test suite currently covers selected product browsing and shopping cart scenarios. It does not represent exhaustive validation of the entire application.
