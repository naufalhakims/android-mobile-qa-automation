# Negative Testing Report

**Execution date:** 2026-10-14 (Asia/Jakarta)  
**Scope:** Validation messages observed in the bundled Sauce Labs My Demo App APK.

## Scenarios and observed behavior

| Area | Input | Observed app behavior | Assertion |
|---|---|---|---|
| Login | App-listed `alice@example.com (locked out)` demo account | `Sorry, this user has been locked out.`; login remains open | Exact message and no navigation |
| Shipping | Submit all fields empty | `Please provide your full name.`, `Please provide your address.`, `Please provide your city.`, `Please provide your zip code.`, `Please provide your country.` | Exact message for all five fields; form remains open |
| Payment | Submit all fields empty | Expiration Date and Security Code each show `Value looks invalid.` | Exact visible error text for those two fields |

## Validation boundaries

The empty payment submission did not expose visible error text for `Full Name*` or `Card Number*` in the app's UI hierarchy; the test therefore does not invent required-field messages for them. Exploratory malformed values (expiration `13/99` and security code `12`) also produced no visible validation message. No formatting rule is claimed or asserted for those values.

## Regression outcome

The individual negative checks passed in earlier local runs. The consolidated negative regression method is committed in `CheckoutNegativeTest`, but the latest attempt could not start because the UiAutomator2 instrumentation process timed out during session creation. That is an environment/session startup error, not a validation assertion failure. Re-run with the emulator booted and responsive before treating the consolidated suite as verified.

When a test fails after an Appium session is active, `FailureArtifactExtension` writes a PNG screenshot, UI hierarchy XML, and failure message to `target/failure-artifacts/`.
