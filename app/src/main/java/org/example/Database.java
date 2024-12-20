package org.example;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import io.javalin.config.Key;

public class Database {
    private static final Jdbi jdbi;
    private static final String URL = DatabaseConfig.getDbUrl();
    private static final String USER = DatabaseConfig.getDbUsername();
    private static final String PASSWORD = DatabaseConfig.getDbPassword();

    static {
        jdbi = Jdbi.create(URL, USER, PASSWORD)
                .installPlugin(new PostgresPlugin())
                .installPlugin(new SqlObjectPlugin());
    }

    public static Jdbi getJdbi() {
        return jdbi;
    }

    public static Key<Jdbi> dbKey() {
        return new Key<>("db");
    }
}
