package pageFactory;

import dataProvider.ConfigFileReader;
import managers.FileReaderManager;
import objectRepository.HomePageFlightsObjects;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

    public void closeQRCodePopup(){
        if(homePageFlightsObjects.getQRCodePopup().isDisplayed()){
            try{
                homePageFlightsObjects.getQRCodePopupClose().click();
                System.out.println("QR Code pop-up closed");
            } catch (Exception e){
                throw new RuntimeException("Failed to close pop-up", e);
            }
        } else {
            System.out.println("QR Code pop-up is not displayed");
        }
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

    public void fromCity() {
        // To delete the pre-populated value
        homePageFlightsObjects.getOriginCity().sendKeys(Keys.CONTROL + "a");
        homePageFlightsObjects.getOriginCity().sendKeys(Keys.DELETE);
        //JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        //jsExecutor.executeScript("arguments[0].value = '';", homePageFlightsObjects.getOriginCity());
        homePageFlightsObjects.getOriginCity().sendKeys("Bengaluru");
        homePageFlightsObjects.getFirstCityFromOriginCityDropdown().click();
    }

    public void toCity() {
        homePageFlightsObjects.getDestinationCity().sendKeys("Bangkok");
        wait.until(ExpectedConditions.elementToBeClickable(homePageFlightsObjects.getFirstCityFromDestinationCityDropdown()));
        homePageFlightsObjects.getFirstCityFromDestinationCityDropdown().click();
    }

    public void openDatePicker(){
        if(homePageFlightsObjects.getDatePicker().isDisplayed()){
            homePageFlightsObjects.getDatePickerResetBtn().click();
        } else {
            homePageFlightsObjects.getDepartDateField().click();
            homePageFlightsObjects.getDatePickerResetBtn().click();
        }
    }

    public void departDate(){
        homePageFlightsObjects.selectDepartDate("2025-8-10");
    }

    public void returnDate(){
        homePageFlightsObjects.selectReturnDate("2025-8-14");
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
