package com.amalitech.testautomation.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.amalitech.testautomation.support.VisualActions;

/**
 * Represents the newsletter sign-up card, covering both its sign-up state
 * and the success state it switches to after a valid submission.
 */
public class NewsletterSignUpPage {

    private static final String URL = "https://quality-assurance-labs.vercel.app";

    private final WebDriver driver;
    private final VisualActions actions;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(css = "button[type='submit']")
    private WebElement submitButton;

    @FindBy(css = ".success-view__title")
    private WebElement successMessage;

    public NewsletterSignUpPage(WebDriver driver) {
        this.driver = driver;
        this.actions = new VisualActions(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(URL);
    }

    public void enterEmail(String email) {
        actions.type(emailInput, email, "Enter email address");
    }

    public void submit() {
        actions.click(submitButton, "Click submit button");
    }

    public String getSuccessMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(successMessage));
        return successMessage.getText();
    }
}
