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
import io.restassured.response.Response;

import static org.junit.Assert.*;

public class AccountNavigationTest {
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    private static final String LOGIN_URL = BASE_URL + "/login";
    private static final String PROFILE_URL = BASE_URL + "/account/profile";
    private static final String MAIN_PAGE_URL = BASE_URL + "/";

    private WebDriver driver;
    private UserClient userClient;
    private UserData userData;
    private String accessToken;
    private MainPage mainPage;

    @Before
    @Step("Подготовка тестового окружения")
    public void setUp() {
        driver = Browser.createWebDriver();
        mainPage = new MainPage(driver);
        userClient = new UserClient();

        // Создание тестового пользователя
        String email = "testuser_" + System.currentTimeMillis() + "@example.com";
        String password = "password_" + System.currentTimeMillis();
        String name = "TestUser_" + System.currentTimeMillis();
        userData = new UserData(email, password, name);

        userClient.register(userData);
        loginUser();
        driver.get(MAIN_PAGE_URL);
    }

    @After
    @Step("Очистка тестового окружения")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }

    @Step("Авторизация пользователя")
    private void loginUser() {
        driver.get(LOGIN_URL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setEmail(userData.getEmail());
        loginPage.setPassword(userData.getPassword());
        loginPage.clickLoginButton();

        // Исправлено: создаем UserCredentials через конструктор
        UserCredentials credentials = new UserCredentials(userData.getEmail(), userData.getPassword());
        Response loginResponse = userClient.login(credentials);
        accessToken = loginResponse.then().extract().path("accessToken");
    }

    @Test
    @DisplayName("Переход в личный кабинет")
    @Description("Проверка перехода в личный кабинет по клику на кнопку 'Личный кабинет'")
    public void navigateToPersonalAccountTest() {
        mainPage.clickPersonalCabinetButton();
        assertTrue("Не произошел переход в личный кабинет",
                driver.getCurrentUrl().contains(PROFILE_URL));
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по кнопке 'Конструктор'")
    @Description("Проверка перехода из личного кабинета в конструктор по кнопке 'Конструктор'")
    public void navigateFromAccountToConstructorViaButtonTest() {
        mainPage.clickPersonalCabinetButton();
        AccountPage accountPage = new AccountPage(driver);
        accountPage.clickConstructorButton();
        assertEquals("Не произошел переход на главную страницу",
                MAIN_PAGE_URL, driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на логотип")
    @Description("Проверка перехода из личного кабинета в конструктор по клику на логотип Stellar Burgers")
    public void navigateFromAccountToConstructorViaLogoTest() {
        mainPage.clickPersonalCabinetButton();
        AccountPage accountPage = new AccountPage(driver);
        accountPage.clickLogo();
        assertEquals("Не произошел переход на главную страницу",
                MAIN_PAGE_URL, driver.getCurrentUrl());
    }
}