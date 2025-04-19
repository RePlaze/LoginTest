package com.example.tests;

import com.example.pages.HomePage;
import com.example.pages.LoginPage;
import com.example.utils.TestLogger;
import com.example.utils.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTests {

    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        driver = WebDriverFactory.createDriver();
        driver.get("https://the-internet.herokuapp.com/login");
        loginPage = new LoginPage(driver);
        TestLogger.info("Браузер инициализирован и открыта страница входа.");
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][]{
                {"tomsmith", "SuperSecretPassword!", true},
                {"wronguser", "wrongpassword", false}
        };
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password, boolean isLoginSuccessful) {
        TestLogger.info("Начало теста входа для пользователя: " + username);
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Страница входа не отображается");

        loginPage.login(username, password);

        if (isLoginSuccessful) {
            homePage = new HomePage(driver);
            Assert.assertTrue(homePage.isHomePageDisplayed(), "Главная страница не отображается после входа");
            TestLogger.info("Вход успешен для пользователя: " + username);
        } else {
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Сообщение об ошибке не отображается при некорректном входе");
            TestLogger.info("Вход не удался для пользователя: " + username);
        }
    }

    @Test
    public void testLogout() {
        loginPage.login("tomsmith", "SuperSecretPassword!");
        homePage = new HomePage(driver);

        homePage.logout();
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Страница входа не отображается после выхода");
        TestLogger.info("Тест выхода из системы завершен.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            TestLogger.info("Браузер закрыт.");
        }
    }
}
