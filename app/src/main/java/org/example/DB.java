package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    public static Connection connect() throws SQLException {
        try {
            String jdbcUrl = DatabaseConfig.getDbUrl();
            String user = DatabaseConfig.getDbUsername();
            String password = DatabaseConfig.getDbPassword();

            return DriverManager.getConnection(jdbcUrl, user, password);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
