package com.automation.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.automation.utils.ConfigReader;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * BaseTest - Base class for all test classes.
 * Opens ONE browser for all tests in the class, navigates to base URL before each test,
 * and closes the browser once after all tests finish.
 */
public class BaseTest {

    protected WebDriver driver;
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    /** Runs ONCE before all tests — opens the browser. */
    @BeforeClass(alwaysRun = true)
    public void setUp() {
        logger.info("Setting up WebDriver...");

        String browser = ConfigReader.getBrowser().toLowerCase();

        switch (browser) {
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

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(buildChromeOptions());
                logger.info("Chrome driver initialized");
                break;
        }

        // Timeouts and window
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().window().maximize();

        // Hide the webdriver flag so Google does not flag the session as a bot
        ((JavascriptExecutor) driver).executeScript(
            "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");

        logger.info("WebDriver setup complete — browser is open");
    }

    /**
     * Runs before EACH test.
     * Tests 1-5 get a fresh navigation to the base URL.
     * Tests 6-8 continue directly from where the previous image test left off —
     * they share the same Google Images results page opened by test 5.
     */
    @BeforeMethod(alwaysRun = true)
    public void navigateToBaseUrl(Method method) {
        String testName = method.getName();
        if (testName.equals("testCountNaturePhotos")
                || testName.equals("testClickSunsetPhoto")
                || testName.equals("testVerifyPhotoDetails")) {
            logger.info("Image flow — skipping base URL navigation for: " + testName);
            return;
        }
        driver.navigate().to(ConfigReader.getBaseUrl());
        logger.info("Navigated to base URL: " + ConfigReader.getBaseUrl());
    }

    /** Runs ONCE after all tests — closes the browser. */
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        logger.info("Closing WebDriver...");
        if (driver != null) {
            driver.quit();
            logger.info("WebDriver closed successfully");
        }
    }

    /**
     * Builds ChromeOptions with anti-bot-detection flags so Google does not
     * redirect the session to the /sorry/ captcha page.
     *
     * Headless mode is enabled automatically when:
     *   - the JVM property  -Dheadless=true  is passed, OR
     *   - the CI environment variable is set (GitHub Actions sets this by default)
     */
    private ChromeOptions buildChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setAcceptInsecureCerts(true);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);

        // Headless mode: activated by -Dheadless=true or the CI environment variable
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"))
                || System.getenv("CI") != null;
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            logger.info("Running in HEADLESS mode");
        }

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", prefs);
        return options;
    }
}
