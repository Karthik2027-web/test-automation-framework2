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

    /**
     * Test 6: Count nature photo thumbnails in Google Images results
     * Verifies that at least one image thumbnail is loaded on the results page.
     */
    @Test(priority = 6, description = "Count image thumbnails in Google Images results")
    public void testCountNaturePhotos() {
        logger.info("Starting test: testCountNaturePhotos");
        samplePage = new SamplePage(driver);

        samplePage.searchForNaturePhoto("nature photo");

        int count = samplePage.getImageResultsCount();
        logger.info("Number of image thumbnails found: " + count);

        Assert.assertTrue(count > 0,
                "Google Images should display at least one thumbnail, but found: " + count);

        logger.info("Test passed: Found " + count + " nature photo thumbnails");
    }

    /**
     * Test 7: Click the first nature photo and verify the detail view opens
     * Clicks the first thumbnail in Google Images and checks that
     * the image detail panel or preview opens successfully.
     */
    @Test(priority = 7, description = "Click first nature photo and verify detail view opens")
    public void testClickNaturePhoto() {
        logger.info("Starting test: testClickNaturePhoto");
        samplePage = new SamplePage(driver);

        samplePage.searchForNaturePhoto("nature photo");

        boolean clicked = samplePage.clickFirstNaturePhoto();
        Assert.assertTrue(clicked,
                "Should be able to click the first nature photo thumbnail");

        boolean detailOpen = samplePage.isPhotoDetailViewOpen();
        Assert.assertTrue(detailOpen,
                "Photo detail view should open after clicking the thumbnail");

        logger.info("Test passed: Clicked first nature photo and detail view opened");
    }

    /**
     * Test 8: Verify photo details are shown after clicking a nature photo
     * After clicking a thumbnail, checks that the detail view shows
     * a non-empty title (the source page title or image description).
     */
    @Test(priority = 8, description = "Verify photo details are displayed after clicking")
    public void testVerifyPhotoDetails() {
        logger.info("Starting test: testVerifyPhotoDetails");
        samplePage = new SamplePage(driver);

        samplePage.searchForNaturePhoto("nature photo");
        samplePage.clickFirstNaturePhoto();

        String title = samplePage.getPhotoDetailTitle();
        logger.info("Photo detail title: " + title);

        Assert.assertNotNull(title, "Page title should not be null after clicking a photo");
        Assert.assertFalse(title.isEmpty(), "Page title should not be empty after clicking a photo");

        logger.info("Test passed: Photo detail shows title — '" + title + "'");
    }
}
