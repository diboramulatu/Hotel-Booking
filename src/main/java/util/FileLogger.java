package util;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLogger {
    private static final String LOG_FILE = "log.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public static void log(String level, String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = String.format("[%s] %s: %s%n", timestamp, level, message);
        
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(logEntry);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
    
    public static void info(String message) {
        log("INFO", message);
    }
    
    public static void error(String message) {
        log("ERROR", message);
    }
    
    public static void warn(String message) {
        log("WARN", message);
    }
    
    public static void debug(String message) {
        log("DEBUG", message);
    }
}
