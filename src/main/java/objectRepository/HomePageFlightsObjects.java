package objectRepository;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePageFlightsObjects {
    private final WebDriver driver;

    public HomePageFlightsObjects(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
        }
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Getter
    @FindBy(xpath = "//div[contains(@class, 'QRPopUp__PopUpContainer')]")
    private WebElement QRCodePopup;

    @Getter
    @FindBy(xpath = "//div[contains(@class, 'QRPopUp__CloseButton')]")
    private WebElement QRCodePopupClose;

    @Getter
    @FindBy(xpath = "//p[normalize-space()='Flights']")
    private WebElement flightButton; //Verify isSelected, if not then select

    @Getter
    @FindBy(css = "#home_triptype")
    private WebElement selectTripType; //click, dropdown will be displayed


    public WebElement selectJourneyType(String testIdValue) {
        var locator = By.xpath(String.format("//div[@id='%s']", testIdValue));
        return driver.findElement(locator); //pass value and perform click, values will be home_roundtrip, home_oneway
    }

    @Getter
    @FindBy(xpath = "//input[@id='flight-place-picker']")
    private WebElement originCity; //click, clear and send values

    @Getter
    @FindBy(xpath = "//div[contains(@class, 'Dropdown__OptionsContainer')]/ul/li[1]") //put ExplicitWait/fluent wait and use
    private WebElement firstCityFromOriginCityDropdown; //perform click
    // if we want to use .sendKeys(Keys.ARROW_DOWN + Keys.ENTER) as an alternative, after sending values only we will do that, this locator not req.
    /*
    can be improved further based on sent values in origin city.
    After selecting city from dropdown, perform getText and display the same.
     */

    @Getter
    @FindBy(css = "#home_flyingfrom")
    private WebElement destinationCity; //click and send values

    @Getter
    @FindBy(xpath = "//div[contains(@class, 'Dropdown__OptionsContainer')]/ul/li[1]") //put ExplicitWait/fluent wait and use
    private WebElement firstCityFromDestinationCityDropdown; //perform click
    // if we want to use .sendKeys(Keys.ARROW_DOWN + Keys.ENTER) as an alternative, after sending values only we will do that, this locator not req.
    /*
     can be improved further based on sent values in destination city.
     After selecting city from dropdown, perform getText and display the same.
     */

    @Getter
    @FindBy(xpath = "//div[contains(@class, 'Placepicker__IconWrapperInner')]")
    private WebElement swapOriginDestination; //It's a button, click

    @Getter
    @FindBy(xpath = "//input[contains(@label, 'Depart')]")
    private WebElement departDateField; // Onclick date picker will be displayed, before clicking see whether date picker is displayed by using the below locator

    @Getter
    @FindBy(xpath = "//a[normalize-space()='Reset']")
    private WebElement datePickerResetBtn;

    @Getter
    @FindBy(css = "#calenderbody")
    private WebElement datePicker; // use for assertion. To assert, date picker is displayed, if this returns true then do not click on departDateField locator

    public void departMonth(String departMonthYear){
        var dMonth = By.xpath(String.format("//div[text()='%s']", departMonthYear));
        driver.findElement(dMonth);
        //departMonthYear value should be 'August 2025', pick this from the departureDate, assert it in datePicker like isthere?, if it's not then click next month button
    }

    public void selectDepartDate(String departureDate){
        var dDate =  By.xpath(String.format("//div[@id='div-%s']", departureDate));
        driver.findElement(dDate);
        // user can give any date, we need to convert it in this format 2025-6-29, i.e., YYYY-MM-DD, remove one zero in the month
    }

    public void returnMonth(String returnMonthYear){
        var dMonth = By.xpath(String.format("//div[text()='%s']", returnMonthYear));
        driver.findElement(dMonth);
        //departMonthYear value should be 'August 2025', pick this from the departureDate, assert it in datePicker like isthere?, if it's not then click next month button
    }

    public void selectReturnDate(String returnDate){
        var rDate = By.xpath(String.format("//div[@id='div-%s']", returnDate));
        driver.findElement(rDate);
    }

    /*
     Before selecting the date, when date picker is displayed, from departure date we need to match that the month
     is displayed in the date picker window correctly and then select the date
    */
    /* The Departure date should be greater than or equal to the return date */

    @Getter
    @FindBy(xpath = "//div[@id='righticon']")
    private WebElement goToNextMonth; // click operation, after clicking, assert the month you are in

    @Getter
    @FindBy(xpath = "//div[@id='lefticon']")
    private WebElement goToPreviousMonth; // click operation, after clicking, assert the month you are in

    @Getter
    @FindBy(xpath = "//a[@id='closebutton']")
    private WebElement confirmJourneyDate; // click operation, after selecting dates.Scroll to the element and click like below
    /*
    public void scrollAndClickApplyButton() {
        SeleniumActions.scrollToElement(driver, datePickerApply);
        confirmJourneyDate.click();
    }
    */

    @Getter
    @FindBy(css = "#home_paxdetails")
    private WebElement travellerCabinClass; // onClick,
    // a window will be displayed
    // for selecting cabin class and number of travelers

    public void cabinClassSelection(String cabinClass){
        var cabin = By.xpath(String.format("//div[@id='home_%s']", cabinClass));
        driver.findElement(cabin);
        // cabinClass value should be economy, business, premium_economy, firstclass
    }

    /**
     * Adjusts passenger count for "Adults", "Children", etc., based on given value.
     *
     * @param driver        WebDriver instance
     * @param passengerType "adult" or "child" or "infant"
     * @param expectedCount Desired number of passengers
     */
    public void adjustPassengerCount(WebDriver driver, String passengerType, int expectedCount) {
        var moreButtonLocator = By.xpath(String.format("//div[@id='home_%s_add']", passengerType));
        var fewerButtonLocator = By.xpath(String.format("//div[@id='home_%s_remove']", passengerType));
        var countDisplayLocator = By.xpath(String.format("//div[@id='home_%s_remove']/following-sibling::h3", passengerType));

        var addButton = driver.findElement(moreButtonLocator);
        var removeButton = driver.findElement(fewerButtonLocator);
        var countDisplay = driver.findElement(countDisplayLocator);

        var currentCount = Integer.parseInt(countDisplay.getText().trim());
        //verify the selected number of adult passengers against the user provided data
        // and can be used for assertion also

        while (currentCount < expectedCount) {
            addButton.click();
            currentCount++;
        }

        while (currentCount > expectedCount) {
            removeButton.click();
            currentCount--;
        }

        System.out.printf("Passenger count for %s set to: %d%n", passengerType, currentCount);
    }

    @Getter
    @FindBy(xpath = "//a[@id='home_Search']")
    private WebElement searchFlights; // click operation

}
