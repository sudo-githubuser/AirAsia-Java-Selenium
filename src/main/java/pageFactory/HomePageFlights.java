package pageFactory;

import dataProvider.ConfigFileReader;
import managers.FileReaderManager;
import objectRepository.HomePageFlightsObjects;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.SeleniumActions;

import java.time.Duration;

public class HomePageFlights {

    WebDriver driver;
    WebDriverWait wait;
    HomePageFlightsObjects homePageFlightsObjects;
    ConfigFileReader configFileReader;

    public HomePageFlights(WebDriver driver){
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
        }
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.homePageFlightsObjects = new HomePageFlightsObjects(driver);
        configFileReader = new ConfigFileReader();
    }

    public void launchUrl() {
        driver.navigate().to(FileReaderManager.getConfigReader().getApplicationURL());
    }


    public void selectFlight(){
        if(!homePageFlightsObjects.getFlightButton().isSelected()){
            try{
                homePageFlightsObjects.getFlightButton().click();
                System.out.println("Flight button was not selected, so it was clicked and is now selected.");
            } catch (Exception e) {
                throw new RuntimeException("Failed to click and select the flight button.", e);
            }
        } else {System.out.println("Flight button was already selected. No action taken.");}
    }

    public void tripType(){
        homePageFlightsObjects.getSelectTripType().click();
        if(!homePageFlightsObjects.selectJourneyType("home_roundtrip").isSelected()){
            try{
                homePageFlightsObjects.selectJourneyType("home_roundtrip").click();
                System.out.println("Journey type selected as 'Return Journey'");
            } catch (Exception e){
                throw new RuntimeException("Failed to select the journey type", e);
            }
        } else {System.out.println("Journey type is already selected as 'Return Journey'");}
    }

    public void fromCity() throws InterruptedException {
//        homePageFlightsObjects.getOriginCity().click();
        Thread.sleep(8000);
//        homePageFlightsObjects.getOriginCity().clear();
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].value = '';", homePageFlightsObjects.getOriginCity());
        Thread.sleep(8000);
        homePageFlightsObjects.getOriginCity().sendKeys("Bengaluru");
        homePageFlightsObjects.getFirstCityFromOriginCityDropdown().click();
    }

    public void toCity() throws InterruptedException {
        homePageFlightsObjects.getDestinationCity().sendKeys("Bangkok");
        homePageFlightsObjects.getFirstCityFromDestinationCityDropdown().click();
    }

    public void openDatePicker(){
        homePageFlightsObjects.getDepartDateField().click();
    }

    public void departDate(){
        homePageFlightsObjects.selectDepartDate("2025-7-29");
    }

    public void returnDate(){
        homePageFlightsObjects.selectReturnDate("2025-8-11");
    }

    public void applyDates(){
        SeleniumActions.scrollToElement(driver, homePageFlightsObjects.getConfirmJourneyDate());
        homePageFlightsObjects.getConfirmJourneyDate().click();
    }

    public void travelCabinClass(){
        homePageFlightsObjects.getTravellerCabinClass().click();
    }

    public void cabinClassSelection(){
        homePageFlightsObjects.cabinClassSelection("business");
    }

    public void passengerCount(){
        homePageFlightsObjects.adjustPassengerCount(driver, "Adult", 4);
    }

    public void searchFlight(){
        homePageFlightsObjects.getSearchFlights().click();
    }
}
