package com.f1manager.dao;

import com.f1manager.model.Escuderia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EscuderiaDAO {

    public List<Escuderia> findAll() throws SQLException {
        List<Escuderia> escuderias = new ArrayList<>();
        String sql = "SELECT * FROM app_constructors";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Escuderia e = new Escuderia(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("full_name"),
                        rs.getString("nationality"),
                        rs.getDouble("total_points"),
                        rs.getInt("total_race_wins"),
                        rs.getInt("total_podiums"),
                        rs.getInt("total_championship_wins")
                );
                escuderias.add(e);
            }
        }
        return escuderias;
    }

    public Escuderia findById(String id) throws SQLException {
        String sql = "SELECT * FROM app_constructors WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Escuderia(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("full_name"),
                        rs.getString("nationality"),
                        rs.getDouble("total_points"),
                        rs.getInt("total_race_wins"),
                        rs.getInt("total_podiums"),
                        rs.getInt("total_championship_wins")
                );
            }
        }
        return null;
    }

    public void save(Escuderia escuderia) throws SQLException {
        String sql = "INSERT INTO constructor (id, name, full_name, country_id, " +
                "total_championship_wins, total_race_entries, total_race_starts, total_race_wins, " +
                "total_1_and_2_finishes, total_race_laps, total_podiums, total_podium_races, " +
                "total_points, total_championship_points, total_pole_positions, total_fastest_laps, " +
                "total_sprint_race_starts, total_sprint_race_wins) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String countryId = resolveCountryId(conn, escuderia.getNationality());
            stmt.setString(1, normalizeText(escuderia.getId()));
            stmt.setString(2, normalizeText(escuderia.getName()));
            stmt.setString(3, normalizeText(escuderia.getFullName()));
            stmt.setString(4, countryId);
            stmt.setInt(5, valueOrZero(escuderia.getTotalChampionshipWins()));
            stmt.setInt(6, 0); // total_race_entries
            stmt.setInt(7, 0); // total_race_starts
            stmt.setInt(8, valueOrZero(escuderia.getTotalRaceWins()));
            stmt.setInt(9, 0); // total_1_and_2_finishes
            stmt.setInt(10, 0); // total_race_laps
            stmt.setInt(11, valueOrZero(escuderia.getTotalPodiums()));
            stmt.setInt(12, 0); // total_podium_races
            stmt.setDouble(13, valueOrZero(escuderia.getTotalPoints()));
            stmt.setDouble(14, valueOrZero(escuderia.getTotalPoints()));
            stmt.setInt(15, 0); // total_pole_positions
            stmt.setInt(16, 0); // total_fastest_laps
            stmt.setInt(17, 0); // total_sprint_race_starts
            stmt.setInt(18, 0); // total_sprint_race_wins

            stmt.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                // Delete child records first to avoid foreign-key RESTRICT errors.
                executeDelete(conn, "DELETE FROM race_data WHERE constructor_id = ?", id);
                executeDelete(conn, "DELETE FROM race_constructor_standing WHERE constructor_id = ?", id);
                executeDelete(conn, "DELETE FROM season_constructor WHERE constructor_id = ?", id);
                executeDelete(conn, "DELETE FROM season_constructor_standing WHERE constructor_id = ?", id);
                executeDelete(conn, "DELETE FROM season_entrant_constructor WHERE constructor_id = ?", id);
                executeDelete(conn, "DELETE FROM season_entrant_driver WHERE constructor_id = ?", id);
                executeDelete(conn, "DELETE FROM season_entrant_engine WHERE constructor_id = ?", id);
                executeDelete(conn, "DELETE FROM season_entrant_tyre_manufacturer WHERE constructor_id = ?", id);

                // Chassis records depend on constructor, and season_entrant_chassis may depend on chassis.
                executeDelete(conn,
                        "DELETE FROM season_entrant_chassis " +
                        "WHERE constructor_id = ? OR chassis_id IN (SELECT id FROM chassis WHERE constructor_id = ?)",
                        id, id);
                executeDelete(conn, "DELETE FROM chassis WHERE constructor_id = ?", id);

                executeDelete(conn, "DELETE FROM constructor_chronology WHERE constructor_id = ? OR other_constructor_id = ?", id, id);

                // Finally delete the parent constructor.
                executeDelete(conn, "DELETE FROM constructor WHERE id = ?", id);

                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    private void executeDelete(Connection conn, String sql, String... params) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setString(i + 1, params[i]);
            }
            stmt.executeUpdate();
        }
    }

    public void update(Escuderia escuderia) throws SQLException {
        String sql = "UPDATE constructor SET name = ?, full_name = ?, country_id = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String countryId = resolveCountryIdOrCurrent(conn, escuderia.getNationality(), escuderia.getId());
            stmt.setString(1, normalizeText(escuderia.getName()));
            stmt.setString(2, normalizeText(escuderia.getFullName()));
            stmt.setString(3, countryId);
            stmt.setString(4, escuderia.getId());

            stmt.executeUpdate();
        }
    }

    private String normalizeText(String value) {
        if (value == null) return "";
        String trimmed = value.trim();
        return trimmed.length() > 100 ? trimmed.substring(0, 100) : trimmed;
    }

    private int valueOrZero(Integer value) {
        return value == null ? 0 : value;
    }

    private double valueOrZero(Double value) {
        return value == null ? 0.0 : value;
    }

    private String resolveCountryIdOrCurrent(Connection conn, String nationality, String constructorId) throws SQLException {
        try {
            return resolveCountryId(conn, nationality);
        } catch (SQLException ex) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT country_id FROM constructor WHERE id = ?")) {
                stmt.setString(1, constructorId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) return rs.getString("country_id");
                }
            }
            throw ex;
        }
    }

    private String resolveCountryId(Connection conn, String nationality) throws SQLException {
        if (nationality == null || nationality.trim().isEmpty()) {
            return "spain";
        }
        String value = nationality.trim();
        String sql = "SELECT id FROM country WHERE id = ? OR name = ? OR demonym = ? LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, value);
            stmt.setString(2, value);
            stmt.setString(3, value);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getString("id");
            }
        }

        String slug = value.toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
        try (PreparedStatement stmt = conn.prepareStatement("SELECT id FROM country WHERE id = ? LIMIT 1")) {
            stmt.setString(1, slug);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getString("id");
            }
        }
        throw new SQLException("Unknown nationality/country: " + nationality);
    }
}