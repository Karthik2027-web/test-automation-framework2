package com.automation.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.automation.utils.ConfigReader;
import java.time.Duration;

/**
 * BaseTest - Base class for all test classes
 * Handles WebDriver setup and teardown
 */
public class BaseTest {
    
    protected WebDriver driver;
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);
    
    /**
     * Setup method - runs before each test
     */
    @BeforeMethod
    public void setUp() {
        logger.info("Setting up WebDriver...");
        
        String browser = ConfigReader.getBrowser().toLowerCase();
        
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.setAcceptInsecureCerts(true);
                driver = new ChromeDriver(chromeOptions);
                logger.info("Chrome driver initialized");
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setAcceptInsecureCerts(true);
                driver = new FirefoxDriver(firefoxOptions);
                logger.info("Firefox driver initialized");
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--remote-allow-origins=*");
                edgeOptions.setAcceptInsecureCerts(true);
                driver = new EdgeDriver(edgeOptions);
                logger.info("Edge driver initialized");
                break;
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions defaultOptions = new ChromeOptions();
                defaultOptions.addArguments("--remote-allow-origins=*");
                defaultOptions.addArguments("--disable-dev-shm-usage");
                defaultOptions.addArguments("--no-sandbox");
                defaultOptions.addArguments("--disable-gpu");
                defaultOptions.setAcceptInsecureCerts(true);
                driver = new ChromeDriver(defaultOptions);
                logger.info("Default Chrome driver initialized");
        }
        
        // Set timeouts
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        
        // Maximize window
        driver.manage().window().maximize();
        
        // Navigate to base URL
        driver.navigate().to(ConfigReader.getBaseUrl());
        logger.info("Navigated to: " + ConfigReader.getBaseUrl());
    }
    
    /**
     * Teardown method - runs after each test
     */
    @AfterMethod
    public void tearDown() {
        logger.info("Closing WebDriver...");
        if (driver != null) {
            driver.quit();
            logger.info("WebDriver closed successfully");
        }
    }
}
