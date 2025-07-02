package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы
    private final By btnEnter = By.className("button_button__33qZ0");
    private final By btnPersonalAccount = By.cssSelector("a[href='/account']");
    private final By btnOrder = By.xpath("//button[text()='Оформить заказ']");
    private final By btnSauces = By.xpath("//div[contains(@class, 'tab_tab__')]//span[text()='Соусы']/..");
    private final By btnFillings = By.xpath("//div[contains(@class, 'tab_tab__')]//span[text()='Начинки']/..");
    private final By btnBuns = By.xpath("//div[contains(@class, 'tab_tab__')]//span[text()='Булки']/..");
    private final By lblBunsHeader = By.xpath("//h2[contains(@class, 'text_type_main-medium') and text()='Булки']");
    private final By lblSaucesHeader = By.xpath("//h2[contains(@class, 'text_type_main-medium') and text()='Соусы']");
    private final By lblFillingsHeader = By.xpath("//h2[contains(@class, 'text_type_main-medium') and text()='Начинки']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Методы для кликов по кнопкам
    public void clickLoginButton() {
        clickElement(btnEnter);
    }

    public void clickPersonalCabinetButton() {
        clickElement(btnPersonalAccount);
    }

    public void clickOrderButton() {
        clickElement(btnOrder);
    }

    // Методы для работы с разделами конструктора
    public void selectBunsSection() {
        clickElement(btnBuns);
        wait.until(ExpectedConditions.visibilityOfElementLocated(lblBunsHeader));
    }

    public void selectSaucesSection() {
        clickElement(btnSauces);
        wait.until(ExpectedConditions.visibilityOfElementLocated(lblSaucesHeader));
    }

    public void selectFillingsSection() {
        clickElement(btnFillings);
        wait.until(ExpectedConditions.visibilityOfElementLocated(lblFillingsHeader));
    }

    // Методы проверки активности разделов
    public boolean isBunsSectionActive() {
        return isTabActive(btnBuns);
    }

    public boolean isSaucesSectionActive() {
        return isTabActive(btnSauces);
    }

    public boolean isFillingsSectionActive() {
        return isTabActive(btnFillings);
    }

    public String getActiveSection() {
        if (isBunsSectionActive()) return "Булки";
        if (isSaucesSectionActive()) return "Соусы";
        if (isFillingsSectionActive()) return "Начинки";
        return "Неизвестный раздел";
    }

    // Вспомогательные методы
    private void clickElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    private boolean isTabActive(By tabLocator) {
        WebElement tab = wait.until(ExpectedConditions.visibilityOfElementLocated(tabLocator));
        return tab.getAttribute("class").contains("tab_tab_type_current__");
    }

    public boolean isBunsHeaderDisplayed() {
        return isElementDisplayed(lblBunsHeader);
    }

    public boolean isSaucesHeaderDisplayed() {
        return isElementDisplayed(lblSaucesHeader);
    }

    public boolean isFillingsHeaderDisplayed() {
        return isElementDisplayed(lblFillingsHeader);
    }

    private boolean isElementDisplayed(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}