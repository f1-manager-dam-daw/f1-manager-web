package com.f1manager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsDAO {
    
    public Map<String, Object> getSummaryStats() throws SQLException {
        Map<String, Object> stats = new HashMap<>();
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            // totalDrivers
            try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM driver");
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) stats.put("totalDrivers", rs.getInt(1));
            }
            
            // totalConstructors
            try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM constructor");
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) stats.put("totalConstructors", rs.getInt(1));
            }
            
            // totalRaces
            try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM race");
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) stats.put("totalRaces", rs.getInt(1));
            }
            
            // firstSeason and lastSeason
            try (PreparedStatement stmt = conn.prepareStatement("SELECT MIN(year), MAX(year) FROM season");
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    stats.put("firstSeason", rs.getInt(1));
                    stats.put("lastSeason", rs.getInt(2));
                }
            }
            
            // topDrivers (top 10 by total_race_wins)
            List<Map<String, Object>> topDrivers = new ArrayList<>();
            try (PreparedStatement stmt = conn.prepareStatement("SELECT forename, surname, nationality, total_race_wins FROM app_drivers ORDER BY total_race_wins DESC LIMIT 10");
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> driver = new HashMap<>();
                    driver.put("forename", rs.getString("forename"));
                    driver.put("surname", rs.getString("surname"));
                    driver.put("nationality", rs.getString("nationality"));
                    driver.put("total_race_wins", rs.getInt("total_race_wins"));
                    topDrivers.add(driver);
                }
            }
            stats.put("topDrivers", topDrivers);
            
            // topConstructors (top 10 by total_race_wins)
            List<Map<String, Object>> topConstructors = new ArrayList<>();
            try (PreparedStatement stmt = conn.prepareStatement("SELECT name, nationality, total_race_wins FROM app_constructors ORDER BY total_race_wins DESC LIMIT 10");
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> constructor = new HashMap<>();
                    constructor.put("name", rs.getString("name"));
                    constructor.put("nationality", rs.getString("nationality"));
                    constructor.put("total_race_wins", rs.getInt("total_race_wins"));
                    topConstructors.add(constructor);
                }
            }
            stats.put("topConstructors", topConstructors);
        }
        
        return stats;
    }
}
