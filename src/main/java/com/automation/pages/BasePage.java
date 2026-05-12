package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.automation.utils.ConfigReader;
import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * BasePage - Base class for all Page Object classes
 * Contains common methods and WebDriver initialization
 */
public class BasePage {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    
    /**
     * Constructor to initialize WebDriver and WebDriverWait
     * @param driver WebDriver instance
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Wait for element to be visible and return the element
     * @param element WebElement to wait for
     * @return WebElement once visible
     */
    protected WebElement waitForElement(WebElement element) {
        logger.info("Waiting for element to be visible");
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    /**
     * Wait for element to be clickable and click
     * @param element WebElement to click
     */
    protected void clickElement(WebElement element) {
        logger.info("Clicking on element");
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
    
    /**
     * Send text to element after clearing it
     * @param element WebElement to type in
     * @param text Text to send
     */
    protected void sendText(WebElement element, String text) {
        logger.info("Sending text: " + text);
        WebElement visibleElement = waitForElement(element);
        visibleElement.clear();
        visibleElement.sendKeys(text);
    }
    
    /**
     * Get text from element
     * @param element WebElement to get text from
     * @return Text of the element
     */
    protected String getText(WebElement element) {
        logger.info("Getting text from element");
        return waitForElement(element).getText();
    }
    
    /**
     * Navigate to URL
     * @param url URL to navigate
     */
    protected void navigateToUrl(String url) {
        logger.info("Navigating to: " + url);
        driver.navigate().to(url);
    }
    
    /**
     * Get current URL
     * @return Current URL
     */
    public String getCurrentUrl() {
        logger.info("Getting current URL");
        return driver.getCurrentUrl();
    }
    
    /**
     * Get page title
     * @return Page title
     */
    public String getPageTitle() {
        logger.info("Getting page title");
        return driver.getTitle();
    }
}
