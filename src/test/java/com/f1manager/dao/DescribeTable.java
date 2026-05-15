package com.f1manager.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DescribeTable {
    public static void main(String[] args) {
        String[] tables = {"driver", "constructor", "race_data", "race_result"};
        for (String tableName : tables) {
            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("DESCRIBE " + tableName)) {
                
                System.out.println("--- Table: " + tableName + " ---");
                System.out.println("Field | Type | Null | Key | Default | Extra");
                while (rs.next()) {
                    System.out.printf("%s | %s | %s | %s | %s | %s%n",
                        rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6));
                }
            } catch (Exception e) {
                System.err.println("Error describing table " + tableName + ": " + e.getMessage());
            }
        }
    }
}
