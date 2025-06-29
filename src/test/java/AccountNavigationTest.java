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
import pages.*;
import generators.UserGenerator;
import io.restassured.response.Response;

import static org.junit.Assert.*;

public class AccountNavigationTest {
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
        loginUser();
        driver.get("https://stellarburgers.nomoreparties.site/");
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

    private void loginUser() {
        driver.get("https://stellarburgers.nomoreparties.site/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setEmail(userData.getEmail());
        loginPage.setPassword(userData.getPassword());
        loginPage.clickLoginButton();
        Response loginResponse = userClient.login(new UserCredentials(userData.getEmail(), userData.getPassword()));
        accessToken = loginResponse.then().extract().path("accessToken");
    }

    @Test
    @DisplayName("Переход в личный кабинет")
    @Description("Проверка перехода в личный кабинет по клику на кнопку 'Личный кабинет'")
    public void navigateToPersonalAccountTest() {
        mainPage.clickPersonalCabinetButton();
        assertTrue("Не произошел переход в личный кабинет", driver.getCurrentUrl().contains("/account/profile"));
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по кнопке 'Конструктор'")
    @Description("Проверка перехода из личного кабинета в конструктор по кнопке 'Конструктор'")
    public void navigateFromAccountToConstructorViaButtonTest() {
        mainPage.clickPersonalCabinetButton();
        AccountPage accountPage = new AccountPage(driver);
        accountPage.clickConstructorButton();
        assertEquals("https://stellarburgers.nomoreparties.site/", driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на логотип")
    @Description("Проверка перехода из личного кабинета в конструктор по клику на логотип Stellar Burgers")
    public void navigateFromAccountToConstructorViaLogoTest() {
        mainPage.clickPersonalCabinetButton();
        AccountPage accountPage = new AccountPage(driver);
        accountPage.clickLogo();
        assertEquals("https://stellarburgers.nomoreparties.site/", driver.getCurrentUrl());
    }
}