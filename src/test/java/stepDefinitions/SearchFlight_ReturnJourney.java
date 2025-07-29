package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import objectRepository.HomePageFlightsObjects;
import pageFactory.HomePageFlights;
import testContext.TestContext;

import java.awt.*;

public class SearchFlight_ReturnJourney {

    TestContext testContext;
    HomePageFlights homePageFlights;
    HomePageFlightsObjects homePageFlightsObjects;

    public SearchFlight_ReturnJourney(TestContext context){
        testContext = context;
        homePageFlights = testContext.getPageObjectManager().getHomePageFlights();
        homePageFlightsObjects = testContext.getPageObjectManager().getHomePageFlightsObjects();
    }

    @Given("User visits Air Asia website")
    public void userVisitsSkyscannerWebsite() {
        homePageFlights.launchUrl();
        homePageFlights.closeQRCodePopup();
    }

    @And("Selects Flight")
    public void selectsFlight() {
        homePageFlights.selectFlight();
        homePageFlights.tripType();
    }

    @When("User enters {string} city")
    public void entersFromCity(String arg0) {
        homePageFlights.fromCity();
    }

    @When("Enters {string} city")
    public void entersToCity(String arg0) {
        homePageFlights.toCity();
    }


    @When("Search flights")
    public void searchFlights() {
        homePageFlights.searchFlight();
    }

    @Then("Flight results are displayed")
    public void flightResultsAreDisplayed() {
    }

    @When("Selects number of travellers as {string}")
    public void selectsNumberOfTravellers(String arg0) {
        homePageFlights.passengerCount();

    }

    @When("Selects cabin class {string}")
    public void selectsCabinClass(String arg0) {
        homePageFlights.travelCabinClass();
        homePageFlights.cabinClassSelection();

    }

    @When("Enters return date as {string}")
    public void entersReturnDate(String arg0) {
        homePageFlights.returnDate();
        homePageFlights.applyDates();

    }

    @When("Enters departure date as {string}")
    public void entersDepartureDate(String arg0) {
        homePageFlights.openDatePicker();
        homePageFlights.departDate();
    }
}
