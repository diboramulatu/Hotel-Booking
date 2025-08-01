package util;

import java.io.*;
import java.util.Properties;

public class ConfigLoader {
    private static final String CONFIG_FILE = "src/main/resources/config.txt";
    private static Properties properties;
    
    static {
        loadConfig();
    }
    
    private static void loadConfig() {
        properties = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Error loading config file: " + e.getMessage());
            // Set default values
            properties.setProperty("db.url", "jdbc:sqlite:hotel.db");
            properties.setProperty("max.stay.days", "30");
        }
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
