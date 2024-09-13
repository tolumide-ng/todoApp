package org.example;

import java.io.IOException;
// import java.sql.Connection;
import java.io.FileInputStream;
import java.util.Properties;

public class DatabaseConfig {
    // Connection connection = null;
    private static final Properties properties = new Properties();

    static {
        try {
            // String appEnv = System.getProperty("APPENV");
            String appEnv = System.getenv("APPENV");
            System.out.println(":::::::::::::::::::::::; " + appEnv);
            if (appEnv == "test") {
                // properties.load(new FileInputStream("src/test/resources/db.properties")); //
                // change back to this
                properties.load(new FileInputStream("src/main/resources/db.properties"));
            } else {
                properties.load(new FileInputStream("src/main/resources/db.properties"));
            }
            Class.forName("org.postgresql.Driver");

        } catch (IOException | ClassNotFoundException e) {
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
