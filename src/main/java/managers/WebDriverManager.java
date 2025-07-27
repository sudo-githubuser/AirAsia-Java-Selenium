package managers;

import enums.DriverType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

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
                driver = new ChromeDriver(options);

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

