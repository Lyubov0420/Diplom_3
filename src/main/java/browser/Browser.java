package browser;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Browser {

    @Step("Создание веб-драйвера для браузера {browser}")
    public static WebDriver createWebDriver() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();

        switch (browser) {
            case "chrome":
                return createChromeDriver();
            case "yandex":
                return createYandexDriver();
            default:
                throw new IllegalArgumentException("Браузер '" + browser + "' не поддерживается");
        }
    }

    @Step("Создание драйвера для Chrome")
    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        return new ChromeDriver(options);
    }

    @Step("Создание драйвера для Yandex Browser")
    private static WebDriver createYandexDriver() {
        WebDriverManager.chromedriver()
                .clearDriverCache()
                .clearResolutionCache()
                .setup();

        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:/Users/katya/AppData/Local/Yandex/YandexBrowser/Application/browser.exe");
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }
}