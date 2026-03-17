package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DriverManager {
    public static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder")
                    .setLevel(Level.OFF);
            Logger.getLogger("org.openqa.selenium.chromium.ChromiumDriver")
                    .setLevel(Level.OFF);

            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--no-sandbox");

            driver = new ChromeDriver(options);
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}