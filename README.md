# Basic Test Automation with Selenium WebDriver

A learning lab on Selenium WebDriver and the practices around it in a real automation setup: structuring tests with JUnit 5, separating page structure from test logic with the Page Object Model, and running the suite through a GitHub Actions CI pipeline.

## Application under test

The tests drive a hosted newsletter sign-up form at https://quality-assurance-labs.vercel.app, a small static page with one email field and a submit button. A valid email reveals a success message; an empty or malformed email reveals a validation error instead, with a distinct message for each case.

## What is tested

One test class, `AppTest`, covers three scenarios rather than only the happy path: submitting a valid email and asserting the success message; submitting an empty field and asserting the required-field error; and a parameterized test over several malformed email shapes (including no domain and no top-level domain), each asserting the invalid-format error.

## How the tests are structured

The form is represented by a single page object, `NewsletterSignUpPage` (`com.amalitech.testautomation.pages`), using the Page Factory pattern with `@FindBy` fields so the test class never touches a locator directly. All waiting for the page to be ready lives inside this page object.

Alongside it, `VisualActions` (`com.amalitech.testautomation.support`) wraps every click and typed input with an explicit wait. It can optionally highlight the element in use and pause between steps, making a run watchable step by step, and it logs every step to the console with a timestamp whether or not visual mode is on.

## Running the tests

By default, running the suite opens a real, visible Chrome window at full speed.

```
mvn test
```

To slow it down, watch each step get highlighted, and read a narrated log of what is happening, set the `debug` and `stepDelay` properties. The delay is in plain milliseconds.

```
mvn test -Ddebug=true -DstepDelay=3000
```

To force a headless run locally, the same way Continuous Integration runs it, set the `headless` property.

```
mvn test -Dheadless=true
```

## Continuous integration

The workflow at `.github/workflows/ci.yml` runs on every push and pull request: it checks out the repo, sets up JDK 11, and runs `mvn test`. The suite detects GitHub Actions' `CI` variable and switches to headless mode on its own, needing no extra workflow config.

A final step posts the build result to Slack via an incoming webhook, formatted as a card showing the repository, branch, trigger, commit, elapsed time, and failed-test count, plus links to the run and commit. It runs on both pass and failure, since a success-only status notification would defeat the purpose.
