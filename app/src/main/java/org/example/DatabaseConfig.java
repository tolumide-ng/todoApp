package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
    private static final Properties properties = new Properties();

    static {
        // DatabaseConfig.class.getResourceAsStream(getDbUsername());
        // try (InputStream input = DatabaseConfig.class.getResourceAsStream("./db.properties")) {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (input == null) {
                    System.out.println("Sorry, unable to find db.properties");
                    System.exit(1);
                }
                
                // InputStream x = DatabaseConfig.getClassLoader().properties;
            properties.load(input);
        } catch (IOException e) {
            System.out.println("||||||||||" + e);
            e.printStackTrace();
        }
    }

    public static String getDbUrl() {
        return properties.getProperty("db.url");
    }

    public static String getDbUsername() {
        return properties.getProperty("db.username");
    }

    public static String getDbPassword() {
        return properties.getProperty("db.password");
    }
}
