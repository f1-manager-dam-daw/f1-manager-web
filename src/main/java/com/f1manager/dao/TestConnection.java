package com.f1manager.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        try {
            System.out.println("Attempting to connect to the database...");
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("SUCCESS: Connected to AWS RDS!");
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("FAILED: Connection error");
            e.printStackTrace();
        }
    }
}
