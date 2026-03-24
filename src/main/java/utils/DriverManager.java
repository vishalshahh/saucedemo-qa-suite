package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DriverManager {
    public static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            // Suppress CDP warnings
            Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder")
                    .setLevel(Level.OFF);
            Logger.getLogger("org.openqa.selenium.chromium.ChromiumDriver")
                    .setLevel(Level.OFF);

            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();

            // ── Core flags ────────────────────────────────
            options.addArguments("--start-maximized");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--no-sandbox");

            // ── Disable ALL Chrome popups ─────────────────
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-extensions");

            // ── Disable password manager completely ───────
            // This is the fix for your "Change your password" popup
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("profile.password_manager_leak_detection", false);

            // Also disable the save password bubble
            prefs.put("profile.default_content_setting_values.notifications", 2);
            options.setExperimentalOption("prefs", prefs);

            // Exclude password manager automation switches
            options.setExperimentalOption("excludeSwitches",
                    new String[]{"enable-automation", "enable-logging"});
            options.addArguments("--password-store=basic");

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