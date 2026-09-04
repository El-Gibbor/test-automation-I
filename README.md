# Basic Test Automation with Selenium WebDriver

Learning lab on Selenium WebDriver along with the practices that surround it in a real test automation setup: structuring tests with JUnit 5, separating page structure from test logic with the Page Object Model, and running the whole suite through a CI pipeline on GitHub Actions.

## Application under test

The tests drive a hosted newsletter sign up form at https://quality-assurance-labs.vercel.app. It is a small static page with one email field and a submit button. Submitting a valid email reveals a success message. Submitting an empty field or a malformed email reveals a validation error instead, with a distinct message for each case.

## What is actually tested

There is one test class, `AppTest`, covering three scenarios rather than only the happy path.

The first scenario submits a valid email and asserts that the success message appears. The second submits the form with the email field left empty and asserts on the required field error. The third is a parameterized test covering several malformed email shapes, including an email with no domain at all and one with a domain that has no top level domain, each asserting on the invalid format error.

## How the tests are structured

The form itself is represented by a single page object, `NewsletterSignUpPage`, under `com.amalitech.testautomation.pages`. It uses the Page Factory pattern, with `@FindBy` annotated fields for its elements, so the test class never touches a locator directly. All the waiting for the page to actually be ready, rather than assuming it is, lives inside this page object too.

Alongside it sits `VisualActions`, under `com.amalitech.testautomation.support`. It wraps every click and every typed input with an explicit wait, and it can optionally highlight the element being interacted with and pause briefly between steps, which makes it possible to actually watch a run happen step by step instead of only reading a pass or fail result afterward. Every step it takes is also logged to the console with a timestamp, whether or not that visual mode is switched on.

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
