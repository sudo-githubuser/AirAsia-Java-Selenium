package managers;

import objectRepository.HomePageFlightsObjects;
import org.openqa.selenium.WebDriver;
import pageFactory.HomePageFlights;


public class PageObjectManager {

    private final WebDriver driver;
    private volatile HomePageFlightsObjects homePageFlightsObjects;
    private volatile HomePageFlights homePageFlights;

    public PageObjectManager(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
        }
        this.driver = driver;
    }

    public HomePageFlightsObjects getHomePageFlightsObjects() {
        if (homePageFlightsObjects == null) {
            synchronized (this) {
                if (homePageFlightsObjects == null) {
                    homePageFlightsObjects = new HomePageFlightsObjects(driver);
                }
            }
        }
        return homePageFlightsObjects;
    }

    public HomePageFlights getHomePageFlights() {
        if (homePageFlights == null) {
            synchronized (this) {
                if (homePageFlights == null) {
                    homePageFlights = new HomePageFlights(driver);
                }
            }
        }
        return homePageFlights;
    }
}

