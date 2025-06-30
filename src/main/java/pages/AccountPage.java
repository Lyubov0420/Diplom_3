package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AccountPage {
    private WebDriverWait wait;

    private By logoutButton = By.xpath(".//button[text()='Выход']");
    private By constructorButton = By.xpath(".//p[text()='Конструктор']");
    private By logo = By.className("AppHeader_header__logo__2D0X2");

    public AccountPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Нажать кнопку 'Выход'")
    public void clickLogoutButton() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        element.click();
    }

    @Step("Нажать кнопку 'Конструктор'")
    public void clickConstructorButton() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(constructorButton));
        element.click();
    }

    @Step("Нажать на логотип")
    public void clickLogo() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(logo));
        element.click();
    }
}