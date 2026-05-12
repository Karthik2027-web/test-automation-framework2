package com.automation.tests;

import com.automation.pages.SamplePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * SampleTest - Sample test class demonstrating Page Object Model
 */
public class SampleTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(SampleTest.class);
    private SamplePage samplePage;

    /**
     * Test 1: Verify Google Homepage loads successfully
     */
    @Test(priority = 1, description = "Verify Google homepage is loaded")
    public void testGoogleHomePage() {
        logger.info("Starting test: testGoogleHomePage");
        samplePage = new SamplePage(driver);

        boolean isDisplayed = samplePage.isSearchBoxVisible();
        Assert.assertTrue(isDisplayed, "Search box should be visible on Google homepage");

        logger.info("Test passed: Google homepage loaded successfully");
    }

    /**
     * Test 2: Verify page title
     */
    @Test(priority = 2, description = "Verify Google page title")
    public void testPageTitle() {
        logger.info("Starting test: testPageTitle");
        samplePage = new SamplePage(driver);

        String title = samplePage.getPageTitle();
        Assert.assertEquals(title, "Google", "Page title should be 'Google'");

        logger.info("Test passed: Page title is correct");
    }

    /**
     * Test 3: Verify current URL
     */
    @Test(priority = 3, description = "Verify current URL")
    public void testCurrentURL() {
        logger.info("Starting test: testCurrentURL");
        samplePage = new SamplePage(driver);

        String url = samplePage.getCurrentUrl();
        Assert.assertTrue(url.contains("google"), "URL should contain 'google'");

        logger.info("Test passed: Current URL is correct");
    }

    /**
     * Test 4: Verify search box is present
     */
    @Test(priority = 4, description = "Verify search box is present")
    public void testSearchBox() {
        logger.info("Starting test: testSearchBox");
        samplePage = new SamplePage(driver);

        Assert.assertTrue(samplePage.isSearchBoxVisible(), "Search box should be visible on Google homepage");

        logger.info("Test passed: Search box is present on Google homepage");
    }

    /**
     * Test 5: Search for a nature photo on Google Images
     * Navigates directly to Google Images search URL for reliability,
     * then verifies the Images results page is displayed.
     */
    @Test(priority = 5, description = "Search for nature photo on Google Images")
    public void testSearchNaturePhoto() {
        logger.info("Starting test: testSearchNaturePhoto");
        samplePage = new SamplePage(driver);

        // Navigate to Google Images search results for "nature photo"
        samplePage.searchForNaturePhoto("nature photo");

        // Verify Google Images results page is displayed
        Assert.assertTrue(samplePage.isImageResultsDisplayed(),
                "Google Images results page should be displayed for 'nature photo'");

        logger.info("Test passed: Nature photo search on Google Images completed successfully");
    }
}
