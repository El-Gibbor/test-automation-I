package com.amalitech.testautomation.support;

import java.time.Duration;
import java.time.Instant;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Wraps element interactions with explicit waits, optional visual
 * debugging (highlighting and slow motion), and step logging.
 */
public class VisualActions {

    private static final boolean DEBUG = Boolean.getBoolean("debug");
    private static final long STEP_DELAY_MS = Long.getLong("stepDelay", 600L);
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);

    private final WebDriver driver;
    private final WebDriverWait wait;

    public VisualActions(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, WAIT_TIMEOUT);
    }

    public void click(WebElement element, String stepLabel) {
        logStep(stepLabel);
        WebElement clickable = wait.until(ExpectedConditions.elementToBeClickable(element));
        highlight(clickable);
        pause();
        clickable.click();
    }

    public void type(WebElement element, String text, String stepLabel) {
        logStep(stepLabel);
        WebElement visible = wait.until(ExpectedConditions.visibilityOf(element));
        highlight(visible);
        pause();
        visible.sendKeys(text);
    }

    private void highlight(WebElement element) {
        if (!DEBUG) {
            return;
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        js.executeScript(
                "arguments[0].style.outline = '1px solid #ff3b3b';"
                        + "arguments[0].style.outlineOffset = '2px';",
                element);
    }

    private void pause() {
        if (!DEBUG) {
            return;
        }
        try {
            Thread.sleep(STEP_DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void logStep(String stepLabel) {
        System.out.println("[" + Instant.now() + "] " + stepLabel);
    }
}
