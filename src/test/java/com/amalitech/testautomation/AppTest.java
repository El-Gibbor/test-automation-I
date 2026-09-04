package com.amalitech.testautomation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.amalitech.testautomation.pages.NewsletterSignUpPage;

/**
 * Drives the newsletter sign-up form through its page object, and verifies
 * that submitting a valid email reveals the success message.
 */
public class AppTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        boolean headless = Boolean.getBoolean("headless")
                || Boolean.parseBoolean(System.getenv("CI"));

        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @Test
    void submittingAValidEmailShowsTheSuccessMessage() {
        NewsletterSignUpPage signUpPage = new NewsletterSignUpPage(driver);

        signUpPage.open();
        signUpPage.enterEmail("chiagoziem.eke@amalitech.com");
        signUpPage.submit();

        assertEquals("Thanks for subscribing!", signUpPage.getSuccessMessage());
    }

    @Test
    void submittingAnEmptyEmailShowsARequiredFieldError() {
        NewsletterSignUpPage signUpPage = new NewsletterSignUpPage(driver);

        signUpPage.open();
        signUpPage.submit();

        assertEquals("Email address is required", signUpPage.getEmailError());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "not-an-email",  // no @ at all
            "user@",         // no domain whatsoever
            "user@example",  // no top-level domain
    })
    void submittingAMalformedEmailShowsAnInvalidFormatError(String malformedEmail) {
        NewsletterSignUpPage signUpPage = new NewsletterSignUpPage(driver);

        signUpPage.open();
        signUpPage.enterEmail(malformedEmail);
        signUpPage.submit();

        assertEquals("Please enter a valid email address", signUpPage.getEmailError());
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
