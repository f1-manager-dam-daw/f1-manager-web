package com.f1manager.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://f1-manager-db.czam0qussyez.us-east-1.rds.amazonaws.com:3306/f1manager";
    private static final String USER = "f1_admin";
    private static final String PASSWORD = "f1_manager";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}