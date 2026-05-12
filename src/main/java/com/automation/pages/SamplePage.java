package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
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
     * Search for a keyword using the Google Search button
     * @param keyword Keyword to search
     */
    public void searchKeyword(String keyword) {
        logger.info("Searching for keyword: " + keyword);
        sendText(searchBox, keyword);
        googleSearchButton.click();
    }

    /**
     * Navigate directly to Google Images search for the given keyword.
     * Using URL navigation is the most reliable way to reach image results
     * without depending on fragile UI elements that change with Google's layout.
     * @param keyword Keyword to search on Google Images
     */
    public void searchForNaturePhoto(String keyword) {
        logger.info("Navigating to Google Images search for: " + keyword);
        String encodedKeyword = keyword.trim().replace(" ", "+");
        String imageSearchUrl = "https://www.google.com/search?q=" + encodedKeyword + "&tbm=isch";
        navigateToUrl(imageSearchUrl);
    }

    /**
     * Verify the current page is a Google Images results page.
     * Checks the URL contains tbm=isch and the page title is not empty.
     * @return true if on a Google Images results page
     */
    public boolean isImageResultsDisplayed() {
        logger.info("Checking if Google Images results are displayed");
        try {
            String currentUrl = driver.getCurrentUrl();
            String pageTitle  = driver.getTitle();
            logger.info("URL: " + currentUrl);
            logger.info("Title: " + pageTitle);
            boolean onImagesPage = currentUrl.contains("tbm=isch");
            boolean pageLoaded   = pageTitle != null && !pageTitle.isEmpty();
            return onImagesPage && pageLoaded;
        } catch (Exception e) {
            logger.error("Image results check failed: " + e.getMessage());
            return false;
        }
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
