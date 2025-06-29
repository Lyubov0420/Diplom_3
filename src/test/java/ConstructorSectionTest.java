import browser.Browser;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.MainPage;

import static org.junit.Assert.*;

public class ConstructorSectionTest {
    private WebDriver driver;
    private MainPage mainPage;
    private final String BASE_URL = "https://stellarburgers.nomoreparties.site/";

    @Before
    public void setUp() {
        driver = Browser.createWebDriver();
        mainPage = new MainPage(driver);
        driver.get(BASE_URL);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Проверка активности раздела 'Булки' по умолчанию")
    @Description("При загрузке страницы должен быть активен раздел 'Булки'")
    public void bunsSectionShouldBeActiveByDefault() {
        assertTrue("Раздел 'Булки' должен быть активен при загрузке",
                mainPage.isBunsSectionActive());
        assertEquals("Булки", mainPage.getActiveSection());
        assertTrue("Заголовок 'Булки' должен отображаться",
                mainPage.isBunsHeaderDisplayed());
    }

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    @Description("Проверка перехода к разделу 'Соусы' в конструкторе")
    public void switchToSaucesSectionTest() {
        // Переключаемся на соусы
        mainPage.selectSaucesSection();

        // Проверяем результат
        assertTrue("Раздел 'Соусы' должен быть активен",
                mainPage.isSaucesSectionActive());
        assertEquals("Соусы", mainPage.getActiveSection());
        assertTrue("Заголовок 'Соусы' должен отображаться",
                mainPage.isSaucesHeaderDisplayed());

        // Проверяем, что булки теперь не активны
        assertFalse("Раздел 'Булки' должен быть неактивен",
                mainPage.isBunsSectionActive());
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    @Description("Проверка перехода к разделу 'Начинки' в конструкторе")
    public void switchToFillingsSectionTest() {
        // Переключаемся на начинки
        mainPage.selectFillingsSection();

        // Проверяем результат
        assertTrue("Раздел 'Начинки' должен быть активен",
                mainPage.isFillingsSectionActive());
        assertEquals("Начинки", mainPage.getActiveSection());
        assertTrue("Заголовок 'Начинки' должен отображаться",
                mainPage.isFillingsHeaderDisplayed());

        // Проверяем, что булки теперь не активны
        assertFalse("Раздел 'Булки' должен быть неактивен",
                mainPage.isBunsSectionActive());
    }

    @Test
    @DisplayName("Возврат к разделу 'Булки'")
    @Description("Проверка возврата к разделу 'Булки' после переключения")
    public void switchBackToBunsSectionTest() {
        // Сначала переключаемся на начинки
        mainPage.selectFillingsSection();
        assertTrue(mainPage.isFillingsSectionActive());

        // Затем возвращаемся к булкам
        mainPage.selectBunsSection();

        // Проверяем результат
        assertTrue("Раздел 'Булки' должен быть активен",
                mainPage.isBunsSectionActive());
        assertEquals("Булки", mainPage.getActiveSection());
        assertTrue("Заголовок 'Булки' должен отображаться",
                mainPage.isBunsHeaderDisplayed());

        // Проверяем, что начинки теперь не активны
        assertFalse("Раздел 'Начинки' должен быть неактивен",
                mainPage.isFillingsSectionActive());
    }
}