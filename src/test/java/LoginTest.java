import browser.Browser;
import clients.UserClient;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import model.UserData;
import model.UserCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.MainPage;
import pages.LoginPage;
import generators.UserGenerator;
import io.restassured.response.Response;
import pages.PasswordRecoveryPage;
import pages.RegistrationPage;

import static org.junit.Assert.*;

public class LoginTest {
    private WebDriver driver;
    private UserClient userClient;
    private UserData userData;
    private String accessToken;
    private MainPage mainPage;

    @Before
    public void setUp() {
        driver = Browser.createWebDriver();
        mainPage = new MainPage(driver);
        userClient = new UserClient();
        String email = UserGenerator.getRandomEmail();
        String password = UserGenerator.getRandomPassword();
        String name = UserGenerator.getRandomName();
        userData = new UserData(email, password, name);
        userClient.register(userData);
        Response loginResponse = userClient.login(new UserCredentials(userData.getEmail(), userData.getPassword()));
        accessToken = loginResponse.then().extract().path("accessToken");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }

    private void loginUserFromLoginPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setEmail(userData.getEmail());
        loginPage.setPassword(userData.getPassword());
        loginPage.clickLoginButton();
    }

    @Test
    @DisplayName("Вход по кнопке 'Войти в аккаунт' на главной")
    @Description("Проверка входа пользователя через кнопку на главной странице")
    public void loginFromMainPageTest() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        mainPage.clickLoginButton();
        loginUserFromLoginPage();
        assertEquals("https://stellarburgers.nomoreparties.site/login", driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    @Description("Проверка входа пользователя через кнопку 'Личный кабинет'")
    public void loginFromPersonalCabinetButtonTest() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        mainPage.clickPersonalCabinetButton();
        loginUserFromLoginPage();
        assertTrue(driver.getCurrentUrl().contains("/login"));
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    @Description("Проверка входа пользователя через кнопку в форме регистрации")
    public void loginFromRegistrationFormTest() {
        driver.get("https://stellarburgers.nomoreparties.site/register");
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.clickLoginLink();
        loginUserFromLoginPage();
        assertEquals("https://stellarburgers.nomoreparties.site/login", driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    @Description("Проверка входа пользователя через кнопку в форме восстановления пароля")
    public void loginFromPasswordRecoveryFormTest() {
        driver.get("https://stellarburgers.nomoreparties.site/forgot-password");
        PasswordRecoveryPage passwordRecoveryPage = new PasswordRecoveryPage(driver);
        passwordRecoveryPage.clickLoginLink();
        loginUserFromLoginPage();
        assertEquals("https://stellarburgers.nomoreparties.site/login", driver.getCurrentUrl());
    }
}