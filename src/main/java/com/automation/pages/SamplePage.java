package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * SamplePage - Page Object for Google Search page
 */
public class SamplePage extends BasePage {
    
    private static final Logger logger = LogManager.getLogger(SamplePage.class);
    
    // Locators
    @FindBy(name = "q")
    private WebElement searchBox;
    
    @FindBy(xpath = "//input[@value='Google Search']")
    private WebElement googleSearchButton;
    
    /**
     * Constructor
     * @param driver WebDriver instance
     */
    public SamplePage(WebDriver driver) {
        super(driver);
    }
    
    /**
     * Search for a keyword
     * @param keyword Keyword to search
     */
    public void searchKeyword(String keyword) {
        logger.info("Searching for keyword: " + keyword);
        sendText(searchBox, keyword);
        googleSearchButton.click();
    }
    
    /**
     * Get search box placeholder
     * @return Placeholder text
     */
    public String getSearchBoxPlaceholder() {
        logger.info("Getting search box placeholder");
        return searchBox.getAttribute("placeholder");
    }
    
    /**
     * Verify search box is visible
     * @return true if visible, false otherwise
     */
    public boolean isSearchBoxVisible() {
        logger.info("Checking if search box is visible");
        try {
            return waitForElement(searchBox).isDisplayed();
        } catch (Exception e) {
            logger.error("Search box is not visible");
            return false;
        }
    }
}
