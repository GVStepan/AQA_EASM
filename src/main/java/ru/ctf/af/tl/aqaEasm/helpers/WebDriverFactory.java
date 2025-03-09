package ru.ctf.af.tl.aqaEasm.helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.IOException;

public class WebDriverFactory {

    public static WebDriver createDriver() throws IOException {
        WebDriver driver = null;
        ConfigReader configs = new ConfigReader();
        String browser = configs.getBrowser();
        boolean isHeadless = configs.isHeadless();

        switch (browser.toLowerCase()) {
            case "edge":
                driver = createEdge(isHeadless);
                break;
            case "firefox":
                driver = createFirefox(isHeadless);
                break;
            default:
                driver = createChrome(isHeadless);
                break;
        }
        return driver;
    }

    /* Тестировалось на ChromeDriver. Сам ChromeDriver в проект не добавлен */
    private static WebDriver createChrome(boolean isHeadless) {
        ChromeOptions chromeOptions = new ChromeOptions();
        if (isHeadless) chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--window-size=1920x1080");
        return new ChromeDriver(chromeOptions);
    }

    /* Для примера, драйвер не добавлен в проект */
    private static WebDriver createEdge(boolean isHeadless) {
        EdgeOptions edgeOptions = new EdgeOptions();
        if (isHeadless) edgeOptions.addArguments("--headless");
        edgeOptions.addArguments("--window-size=1920x1080");
        return new EdgeDriver(edgeOptions);
    }

    /* Для примера, драйвер не добавлен в проект */
    private static WebDriver createFirefox(boolean isHeadless) {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        if (isHeadless) firefoxOptions.addArguments("--headless");
        firefoxOptions.addArguments("--window-size=1920x1080");
        return new FirefoxDriver(firefoxOptions);
    }
}