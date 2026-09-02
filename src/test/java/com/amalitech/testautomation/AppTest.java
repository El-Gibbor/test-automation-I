package com.amalitech.testautomation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Drives the newsletter sign-up form directly, and
 * verifies that submitting a valid email reveals the
 */
public class AppTest {

    private static final String SIGNUP_URL = "https://quality-assurance-labs.vercel.app";

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
    }

    @Test
    void submittingAValidEmailShowsTheSuccessMessage() {
        driver.get(SIGNUP_URL);

        WebElement emailInput = driver.findElement(By.id("email"));
        emailInput.sendKeys("chiagoziem.eke@amalitech.com");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement successTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".success-view__title")));

        assertEquals("Thanks for subscribing!", successTitle.getText());
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
