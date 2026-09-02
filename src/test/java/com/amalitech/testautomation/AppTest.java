package com.amalitech.testautomation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.amalitech.testautomation.pages.NewsletterSignUpPage;

/**
 * Drives the newsletter sign-up form through its page object, and verifies
 * that submitting a valid email reveals the success message.
 */
public class AppTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
    }

    @Test
    void submittingAValidEmailShowsTheSuccessMessage() {
        NewsletterSignUpPage signUpPage = new NewsletterSignUpPage(driver);

        signUpPage.open();
        signUpPage.enterEmail("chiagoziem.eke@amalitech.com");
        signUpPage.submit();

        assertEquals("Thanks for subscribing!", signUpPage.getSuccessMessage());
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
