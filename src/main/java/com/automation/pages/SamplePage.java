package com.automation.pages;

import org.openqa.selenium.JavascriptExecutor;
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

    @FindBy(name = "q")
    private WebElement searchBox;

    @FindBy(xpath = "//input[@value='Google Search']")
    private WebElement googleSearchButton;

    public SamplePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Search for a keyword using the Google Search button.
     */
    public void searchKeyword(String keyword) {
        logger.info("Searching for keyword: " + keyword);
        sendText(searchBox, keyword);
        googleSearchButton.click();
    }

    /**
     * Navigate directly to Google Images search for the given keyword.
     *
     * Google now uses udm=2 for image search (replaced the old tbm=isch).
     * We also re-apply the webdriver flag patch after navigation so the
     * session keeps its non-bot identity across page loads.
     */
    public void searchForNaturePhoto(String keyword) {
        logger.info("Opening Google Images search for: " + keyword);
        String encoded = keyword.trim().replace(" ", "+");
        String url = "https://www.google.com/search?q=" + encoded + "&udm=2";
        navigateToUrl(url);

        // Re-apply the anti-bot patch after each navigation
        try {
            ((JavascriptExecutor) driver).executeScript(
                "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
        } catch (Exception ignored) {
        }
    }

    /**
     * Check whether Google Images results are currently displayed.
     *
     * Accepts both udm=2 (current Google Images) and tbm=isch (legacy).
     * Returns false if Google has redirected to its /sorry/ bot-check page.
     */
    public boolean isImageResultsDisplayed() {
        logger.info("Checking if Google Images results are displayed");
        try {
            String currentUrl = driver.getCurrentUrl();
            String pageTitle  = driver.getTitle();
            logger.info("URL   : " + currentUrl);
            logger.info("Title : " + pageTitle);

            boolean notBlocked    = !currentUrl.contains("/sorry/");
            boolean onImagesPage  = currentUrl.contains("udm=2") || currentUrl.contains("tbm=isch");
            boolean pageLoaded    = pageTitle != null && !pageTitle.isEmpty();

            logger.info("notBlocked=" + notBlocked + "  onImagesPage=" + onImagesPage
                    + "  pageLoaded=" + pageLoaded);
            return notBlocked && onImagesPage && pageLoaded;
        } catch (Exception e) {
            logger.error("Image results check failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get search box placeholder text.
     */
    public String getSearchBoxPlaceholder() {
        logger.info("Getting search box placeholder");
        return searchBox.getAttribute("placeholder");
    }

    /**
     * Verify the search box is visible on the page.
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
