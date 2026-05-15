package com.f1manager.dao;

import com.f1manager.model.Race;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RaceDAO {

    public List<Race> findAll() throws SQLException {
        List<Race> races = new ArrayList<>();
        String sql = "SELECT * FROM app_races ORDER BY year DESC, round ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                races.add(mapResultSetToRace(rs));
            }
        }
        return races;
    }

    public Race findById(int id) throws SQLException {
        String sql = "SELECT * FROM app_races WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToRace(rs);
            }
        }
        return null;
    }

    public List<Race> findByYear(int year) throws SQLException {
        List<Race> races = new ArrayList<>();
        String sql = "SELECT * FROM app_races WHERE year = ? ORDER BY round ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, year);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                races.add(mapResultSetToRace(rs));
            }
        }
        return races;
    }

    private Race mapResultSetToRace(ResultSet rs) throws SQLException {
        return new Race(
            rs.getInt("id"),
            rs.getInt("year"),
            rs.getInt("round"),
            rs.getString("name"),
            rs.getString("official_name"),
            rs.getString("date"),
            rs.getString("circuit_name"),
            rs.getInt("laps"),
            rs.getDouble("distance"),
            rs.getBoolean("drivers_championship_decider"),
            rs.getBoolean("constructors_championship_decider")
        );
    }
}
