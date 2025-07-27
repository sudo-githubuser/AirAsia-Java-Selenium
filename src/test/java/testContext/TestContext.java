package testContext;

import lombok.Getter;
import managers.PageObjectManager;
import managers.WebDriverManager;

@Getter
public class TestContext {
    private final WebDriverManager webDriverManager;
    private final PageObjectManager pageObjectManager;

    public TestContext() {
        webDriverManager = new WebDriverManager();
        pageObjectManager = new PageObjectManager(webDriverManager.getDriver());
    }

}

