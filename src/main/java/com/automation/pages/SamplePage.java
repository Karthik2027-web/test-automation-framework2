package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

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

    /**
     * Count the number of image thumbnails visible in Google Images results.
     * Google serves all thumbnail images from the encrypted-tbn domain.
     */
    public int getImageResultsCount() {
        logger.info("Counting image thumbnails in Google Images results");
        try {
            Thread.sleep(2000);
            List<WebElement> thumbnails = driver.findElements(
                By.cssSelector("img[src*='encrypted-tbn']"));
            logger.info("Found " + thumbnails.size() + " image thumbnails");
            return thumbnails.size();
        } catch (Exception e) {
            logger.error("Failed to count image thumbnails: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Click the first image thumbnail in Google Images results.
     * Uses JavaScript click after scrolling the element into view, which is
     * more reliable than a regular Selenium click on Google's layered divs.
     */
    public boolean clickFirstNaturePhoto() {
        logger.info("Attempting to click the first image thumbnail");
        try {
            Thread.sleep(2000);
            List<WebElement> thumbnails = driver.findElements(
                By.cssSelector("img[src*='encrypted-tbn']"));

            if (thumbnails.isEmpty()) {
                logger.warn("No image thumbnails found — cannot click");
                return false;
            }

            WebElement firstImage = thumbnails.get(0);
            logger.info("Total thumbnails found: " + thumbnails.size() + " — clicking the first one");

            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", firstImage);
            Thread.sleep(1000);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstImage);
            Thread.sleep(2000);

            logger.info("First image thumbnail clicked successfully");
            return true;

        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            return false;
        } catch (Exception e) {
            logger.error("clickFirstNaturePhoto failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check whether the photo detail panel opened after clicking a thumbnail.
     * Tries three signals: URL gained detail parameters, a known panel element
     * is visible, or the page title changed from the search results title.
     */
    public boolean isPhotoDetailViewOpen() {
        logger.info("Checking if photo detail view is open");
        try {
            String url   = driver.getCurrentUrl();
            String title = driver.getTitle();
            logger.info("URL after click  : " + url);
            logger.info("Title after click: " + title);

            if (url.contains("/sorry/")) {
                logger.warn("Bot-detection page shown — aborting check");
                return false;
            }

            // Google adds imgurl / imgrc / imgrefurl to URL when a detail panel opens
            if (url.contains("imgurl") || url.contains("imgrc") || url.contains("imgrefurl")) {
                logger.info("Detail panel detected via URL parameter change");
                return true;
            }

            // Look for known Google Images side-panel elements
            List<WebElement> panels = driver.findElements(
                By.cssSelector("#Sva75c, .islrg, [jsname='figiqf'], [aria-label='Image viewer']"));
            for (WebElement panel : panels) {
                if (panel.isDisplayed()) {
                    logger.info("Detail panel element is visible on the page");
                    return true;
                }
            }

            // Title changes from "nature photo - Google Search" when detail opens
            boolean titleChanged = title != null
                && !title.isEmpty()
                && !title.equals("nature photo - Google Search");
            logger.info("Title changed from search title: " + titleChanged);
            return titleChanged;

        } catch (Exception e) {
            logger.error("isPhotoDetailViewOpen check failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get the page title shown in the photo detail view.
     * The title typically reflects the image source site or a description.
     */
    public String getPhotoDetailTitle() {
        logger.info("Getting page title from photo detail view");
        return driver.getTitle();
    }
}
