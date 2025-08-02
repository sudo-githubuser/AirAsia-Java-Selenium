package managers;

import enums.DriverType;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.Arrays;

public class WebDriverManager {

    private WebDriver driver;
    private final DriverType driverType;

    public WebDriverManager() {
        this.driverType = FileReaderManager.getConfigReader().getBrowser();
    }

    public WebDriver getDriver() {
        if (driver == null) {
            driver = createDriver();
        }
        return driver;
    }

    private WebDriver createDriver() {
        switch (driverType) {
            case FIREFOX -> driver = new FirefoxDriver();
            case CHROME -> {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                // Anti-detection arguments
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-web-security");
                options.addArguments("--allow-running-insecure-content");
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
                options.setExperimentalOption("useAutomationExtension", false);

                driver = new ChromeDriver(options);

                // Execute JavaScript to hide webdriver property
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
                jsExecutor.executeScript("Object.defineProperty(navigator, 'plugins', {get: () => [1, 2, 3, 4, 5]})");
                jsExecutor.executeScript("Object.defineProperty(navigator, 'languages', {get: () => ['en-US', 'en']})");

            }
            case INTERNETEXPLORER -> driver = new InternetExplorerDriver();
            default -> throw new IllegalArgumentException("Unsupported browser: " + driverType);
        }

        driver.manage().timeouts().implicitlyWait(FileReaderManager.getConfigReader().getImplicitWait());
        return driver;
    }

    public void closeDriver() {
        if (driver != null) {
            driver.close();
        }
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null; // Reset to allow re-creation if needed
        }
    }
}

