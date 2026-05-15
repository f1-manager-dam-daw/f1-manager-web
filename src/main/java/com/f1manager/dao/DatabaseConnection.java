package com.f1manager.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("application-local.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (Exception ex) {
            System.err.println("Could not load application-local.properties");
        }
    }

    private static String getConfig(String key, String envKey, String defaultValue) {
        String env = System.getenv(envKey);
        if (env != null && !env.isEmpty()) return env;
        return properties.getProperty(key, defaultValue);
    }

    private static final String URL = "jdbc:mysql://f1-manager-db.czam0qussyez.us-east-1.rds.amazonaws.com:3306/f1manager";
    private static final String USER = "f1_admin";
    private static final String PASSWORD = "f1_manager";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}