package com.example.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebDriverFactory {
    private static final Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);
    private static final String DEFAULT_DRIVER_PATH = "C:\\Users\\nazen\\Desktop\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";
    private static final String CHROME_VERSION_PROPERTY = "chrome.version";

    public static WebDriver createDriver() {
        try {
            String driverPath = getDriverPath();
            String chromeVersion = getChromeVersion();

            logger.info("Инициализация ChromeDriver. Версия Chrome: {}", chromeVersion);
            logger.debug("Путь к ChromeDriver: {}", driverPath);

            System.setProperty("webdriver.chrome.driver", driverPath);

            ChromeOptions options = createChromeOptions();
            WebDriver driver = new ChromeDriver(options);

            logger.info("ChromeDriver успешно инициализирован");
            return driver;

        } catch (Exception e) {
            logger.error("Ошибка при создании WebDriver", e);
            throw new RuntimeException("Не удалось создать экземпляр ChromeDriver. " +
                    "Проверьте совместимость версий Chrome и ChromeDriver.", e);
        }
    }

    private static String getDriverPath() {
        return System.getProperty("webdriver.chrome.driver", DEFAULT_DRIVER_PATH);
    }

    private static ChromeOptions createChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
            "--start-maximized",
            "--remote-allow-origins=*",
            "--disable-notifications",
            "--disable-popup-blocking"
        );
        return options;
    }

    private static String getChromeVersion() {
        try {
            Process process = Runtime.getRuntime().exec(
                "reg query \"HKEY_CURRENT_USER\\Software\\Google\\Chrome\\BLBeacon\" /v version"
            );
            process.waitFor();

            try (java.util.Scanner scanner = new java.util.Scanner(process.getInputStream())) {
                String result = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                int versionIndex = result.indexOf("version");
                if (versionIndex != -1) {
                    return result.substring(versionIndex + 8).trim();
                }
            }
        } catch (Exception e) {
            logger.warn("Не удалось определить версию Chrome", e);
        }
        return "неизвестна";
    }
}
