package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By errorMessage = By.id("flash");
    private By successMessage = By.cssSelector(".flash.success");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private void enterText(By fieldLocator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(fieldLocator));
        element.clear();
        element.sendKeys(text);
    }

    public LoginPage enterUsername(String username) {
        enterText(usernameField, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        enterText(passwordField, password);
        return this;
    }

    public HomePage submitLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        return new HomePage(driver);
    }

    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }

    public boolean isLoginPageDisplayed() {
        return isElementDisplayed(usernameField);
    }

    public boolean isSuccessMessageDisplayed() {
        return isElementDisplayed(successMessage);
    }

    private boolean isElementDisplayed(By elementLocator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public HomePage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        return submitLogin();
    }
}
