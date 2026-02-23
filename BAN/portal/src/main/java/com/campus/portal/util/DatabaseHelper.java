package com.campus.portal.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {

    private static final String DB_FOLDER = "data";
    private static final String DB_NAME = "portal.db";
    private static final String DB_URL;

    static {
        File folder = new File(DB_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String absolutePath = new File(folder, DB_NAME).getAbsolutePath();
        // Use forward slashes – safer for JDBC URL
        DB_URL = "jdbc:sqlite:" + absolutePath.replace('\\', '/');

        // Force load the driver (this is the critical line)
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("SQLite JDBC driver successfully loaded.");
        } catch (ClassNotFoundException e) {
            System.err.println("CRITICAL: SQLite JDBC driver JAR not found on classpath!");
            e.printStackTrace();
            throw new ExceptionInInitializerError("Cannot load org.sqlite.JDBC – check Maven dependency and project rebuild");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        String sql = """
                CREATE TABLE IF NOT EXISTS learners (
                    reg_number TEXT PRIMARY KEY,
                    name TEXT NOT NULL,
                    course TEXT NOT NULL,
                    year_level INTEGER NOT NULL,
                    average_score REAL NOT NULL,
                    email TEXT,
                    contact TEXT,
                    created_on TEXT NOT NULL,
                    status TEXT NOT NULL
                );
                """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Database table 'learners' ready.");
        } catch (SQLException e) {
            System.err.println("Database initialization failed:");
            e.printStackTrace();
        }
    }
}