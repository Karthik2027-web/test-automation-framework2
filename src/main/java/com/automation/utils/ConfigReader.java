package com.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigReader - Utility class to read configuration from properties file
 */
public class ConfigReader {
    
    private static Properties properties;
    
    static {
        try {
            properties = new Properties();
            String configPath = System.getProperty("user.dir") + "/src/test/resources/config.properties";
            FileInputStream fis = new FileInputStream(configPath);
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            System.err.println("Error loading properties file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Get property value by key
     * @param key Property key
     * @return Property value
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * Get browser type from config
     * @return Browser name (chrome, firefox, edge)
     */
    public static String getBrowser() {
        return getProperty("browser");
    }
    
    /**
     * Get base URL from config
     * @return Base URL for testing
     */
    public static String getBaseUrl() {
        return getProperty("base_url");
    }
    
    /**
     * Get implicit wait time
     * @return Wait time in seconds
     */
    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit_wait"));
    }
    
    /**
     * Get explicit wait time
     * @return Wait time in seconds
     */
    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit_wait"));
    }
}
