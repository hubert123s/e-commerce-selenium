package pl.globallogic.drivermanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverManager {
    private static Logger logger = LoggerFactory.getLogger(DriverManager.class);

    public static WebDriver getDriver(String browser){
        try {
            logger.info("Target browser: {}", browser);
            DriverType type = DriverType.valueOf(browser.toUpperCase());
            return switch (type){
                case CHROME -> new ChromeDriver();
                case FIREFOX -> new FirefoxDriver();
            };
        } catch (IllegalArgumentException ex) {
            logger.warn("Unknown browser '{}'. Setting Chrome as a default browser.", browser);
            return new ChromeDriver();
        }

    }
}