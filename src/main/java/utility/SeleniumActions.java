package utility;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SeleniumActions {

    /**
     * Scrolls the page to bring the given WebElement into view.
     *
     * @param driver  the active WebDriver instance
     * @param element the WebElement to scroll to
     */
    public static void scrollToElement(WebDriver driver, WebElement element){
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                element
        );
    }

    /**
     * Selects an option from the dropdown
     *
     * @param visibleText/value/index of the select class
     * @param element the WebElement of the select class
     */

    public static void selectByVisibleText(WebElement element, String visibleText) {
        validateSelectElement(element);
        new Select(element).selectByVisibleText(visibleText);
    }

    public static void selectByValue(WebElement element, String value) {
        validateSelectElement(element);
        new Select(element).selectByValue(value);
    }

    public static void selectByIndex(WebElement element, int index) {
        validateSelectElement(element);
        var dropdown = new Select(element);
        var selectedText = dropdown.getOptions().get(index).getText();
        dropdown.selectByIndex(index);
    }

    private static void validateSelectElement(WebElement element) {
        if (!"select".equalsIgnoreCase(element.getTagName())) {
            throw new IllegalArgumentException("Element is not a <select> tag");
        }
    }

}
