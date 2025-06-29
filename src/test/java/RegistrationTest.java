import browser.Browser;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.UserData;
import model.UserCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.MainPage;
import pages.RegistrationPage;
import pages.LoginPage;
import clients.UserClient;
import generators.UserGenerator;

import static org.junit.Assert.*;

public class RegistrationTest {
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

    private void registerUser() {
        mainPage.clickLoginButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegistrationLink();
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.setName(userData.getName());
        registrationPage.setEmail(userData.getEmail());
        registrationPage.setPassword(userData.getPassword());
        registrationPage.clickRegisterButton();
    }

    @Test
    @DisplayName("Успешная регистрация")
    @Description("Проверка успешной регистрации пользователя с валидными данными")
    public void successfulRegistrationTest() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        registerUser();
        assertTrue(driver.getCurrentUrl().contains("/login"));
        Response loginResponse = userClient.login(new UserCredentials(userData.getEmail(), userData.getPassword()));
        accessToken = loginResponse.then().extract().path("accessToken");
    }

    @Test
    @DisplayName("Ошибка при некорректном пароле")
    @Description("Проверка ошибки при регистрации с паролем меньше 6 символов")
    public void registrationWithIncorrectPasswordTest() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        mainPage.clickLoginButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegistrationLink();
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.setName(userData.getName());
        registrationPage.setEmail(userData.getEmail());
        registrationPage.setPassword("123");
        registrationPage.clickRegisterButton();
        assertTrue(registrationPage.isPasswordErrorVisible());
    }
}