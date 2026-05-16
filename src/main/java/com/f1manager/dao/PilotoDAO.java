package com.f1manager.dao;

import com.f1manager.model.Piloto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PilotoDAO {

    public List<Piloto> findAll() throws SQLException {
        List<Piloto> pilotos = new ArrayList<>();
        String sql = "SELECT * FROM app_drivers";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Piloto p = new Piloto(
                        rs.getString("id"),
                        rs.getString("forename"),
                        rs.getString("surname"),
                        rs.getString("code"),
                        rs.getInt("number"),
                        rs.getString("nationality"),
                        rs.getString("date_of_birth"),
                        rs.getDouble("total_points"),
                        rs.getInt("total_race_wins"),
                        rs.getInt("total_podiums")
                );
                pilotos.add(p);
            }
        }
        return pilotos;
    }

    public Piloto findById(String id) throws SQLException {
        String sql = "SELECT * FROM app_drivers WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Piloto(
                        rs.getString("id"),
                        rs.getString("forename"),
                        rs.getString("surname"),
                        rs.getString("code"),
                        rs.getInt("number"),
                        rs.getString("nationality"),
                        rs.getString("date_of_birth"),
                        rs.getDouble("total_points"),
                        rs.getInt("total_race_wins"),
                        rs.getInt("total_podiums")
                );
            }
        }
        return null;
    }

    public List<Piloto> findByConstructorId(String constructorId) throws SQLException {
        List<Piloto> pilotos = new ArrayList<>();
        String sql = "SELECT DISTINCT d.id, d.first_name AS forename, d.last_name AS surname, " +
                "d.abbreviation AS code, d.permanent_number AS number, nc.demonym AS nationality, " +
                "d.date_of_birth, d.total_points, d.total_race_wins, d.total_podiums " +
                "FROM race_result rr " +
                "JOIN driver d ON d.id = rr.driver_id " +
                "LEFT JOIN country nc ON nc.id = d.nationality_country_id " +
                "WHERE rr.constructor_id = ? " +
                "ORDER BY d.last_name, d.first_name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, constructorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                pilotos.add(new Piloto(
                        rs.getString("id"),
                        rs.getString("forename"),
                        rs.getString("surname"),
                        rs.getString("code"),
                        rs.getInt("number"),
                        rs.getString("nationality"),
                        rs.getString("date_of_birth"),
                        rs.getDouble("total_points"),
                        rs.getInt("total_race_wins"),
                        rs.getInt("total_podiums")
                ));
            }
        }
        return pilotos;
    }

    public void save(Piloto piloto) throws SQLException {
        String sql = "INSERT INTO driver (id, name, first_name, last_name, full_name, abbreviation, permanent_number, gender, date_of_birth, place_of_birth, country_of_birth_country_id, nationality_country_id, total_championship_wins, total_race_entries, total_race_starts, total_race_wins, total_race_laps, total_podiums, total_points, total_championship_points, total_pole_positions, total_fastest_laps, total_sprint_race_starts, total_sprint_race_wins, total_driver_of_the_day, total_grand_slams) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String fullName = normalizeName(piloto.getForename()) + " " + normalizeName(piloto.getSurname());
            String countryId = resolveCountryId(conn, piloto.getNationality());
            stmt.setString(1, piloto.getId());
            stmt.setString(2, fullName);
            stmt.setString(3, normalizeName(piloto.getForename()));
            stmt.setString(4, normalizeName(piloto.getSurname()));
            stmt.setString(5, fullName);
            stmt.setString(6, normalizeCode(piloto.getCode()));
            if (piloto.getNumber() != null) {
                stmt.setString(7, String.valueOf(piloto.getNumber()));
            } else {
                stmt.setNull(7, Types.VARCHAR);
            }
            stmt.setString(8, "M"); // gender
            if (piloto.getDateOfBirth() != null && !piloto.getDateOfBirth().isEmpty()) {
                stmt.setDate(9, java.sql.Date.valueOf(piloto.getDateOfBirth()));
            } else {
                stmt.setDate(9, java.sql.Date.valueOf("1900-01-01")); // Mandatory
            }
            stmt.setString(10, "Unknown"); // place_of_birth
            stmt.setString(11, countryId); // country_of_birth_country_id
            stmt.setString(12, countryId); // nationality_country_id
            stmt.setInt(13, 0); // total_championship_wins
            stmt.setInt(14, 0); // total_race_entries
            stmt.setInt(15, 0); // total_race_starts
            stmt.setInt(16, 0); // total_race_wins
            stmt.setInt(17, 0); // total_race_laps
            stmt.setInt(18, 0); // total_podiums
            stmt.setDouble(19, 0.0); // total_points
            stmt.setDouble(20, 0.0); // total_championship_points
            stmt.setInt(21, 0); // total_pole_positions
            stmt.setInt(22, 0); // total_fastest_laps
            stmt.setInt(23, 0); // total_sprint_race_starts
            stmt.setInt(24, 0); // total_sprint_race_wins
            stmt.setInt(25, 0); // total_driver_of_the_day
            stmt.setInt(26, 0); // total_grand_slams

            stmt.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                // Delete child records first to avoid foreign-key RESTRICT errors.
                executeDelete(conn, "DELETE FROM driver_family_relationship WHERE driver_id = ? OR other_driver_id = ?", id, id);
                executeDelete(conn, "DELETE FROM race_data WHERE driver_id = ?", id);
                executeDelete(conn, "DELETE FROM race_driver_standing WHERE driver_id = ?", id);
                executeDelete(conn, "DELETE FROM season_driver WHERE driver_id = ?", id);
                executeDelete(conn, "DELETE FROM season_driver_standing WHERE driver_id = ?", id);
                executeDelete(conn, "DELETE FROM season_entrant_driver WHERE driver_id = ?", id);

                // Finally delete the parent driver.
                executeDelete(conn, "DELETE FROM driver WHERE id = ?", id);

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
    public void update(Piloto piloto) throws SQLException {
        String sql = "UPDATE driver SET first_name = ?, last_name = ?, name = ?, full_name = ?, abbreviation = ?, nationality_country_id = ?, country_of_birth_country_id = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String fullName = normalizeName(piloto.getForename()) + " " + normalizeName(piloto.getSurname());
            String countryId = resolveCountryIdOrCurrent(conn, piloto.getNationality(), piloto.getId());
            stmt.setString(1, normalizeName(piloto.getForename()));
            stmt.setString(2, normalizeName(piloto.getSurname()));
            stmt.setString(3, fullName);
            stmt.setString(4, fullName);
            stmt.setString(5, normalizeCode(piloto.getCode()));
            stmt.setString(6, countryId);
            stmt.setString(7, countryId);
            stmt.setString(8, piloto.getId());

            stmt.executeUpdate();
        }
    }

    private String normalizeName(String value) {
        if (value == null) {
            return "";
        }
        String trimmed = value.trim();
        return trimmed.length() > 100 ? trimmed.substring(0, 100) : trimmed;
    }

    private String normalizeCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            return "UNK";
        }
        String normalized = code.trim().toUpperCase();
        return normalized.length() > 3 ? normalized.substring(0, 3) : normalized;
    }

    private String resolveCountryIdOrCurrent(Connection conn, String nationality, String driverId) throws SQLException {
        try {
            return resolveCountryId(conn, nationality);
        } catch (SQLException ex) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT nationality_country_id FROM driver WHERE id = ?")) {
                stmt.setString(1, driverId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("nationality_country_id");
                    }
                }
            }
            throw ex;
        }
    }

    private String resolveCountryId(Connection conn, String nationality) throws SQLException {
        if (nationality == null || nationality.trim().isEmpty()) {
            return "unknown";
        }

        String value = nationality.trim();
        String sql = "SELECT id FROM country WHERE id = ? OR name = ? OR demonym = ? LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, value);
            stmt.setString(2, value);
            stmt.setString(3, value);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("id");
                }
            }
        }

        String slug = value.toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
        try (PreparedStatement stmt = conn.prepareStatement("SELECT id FROM country WHERE id = ? LIMIT 1")) {
            stmt.setString(1, slug);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("id");
                }
            }
        }

        throw new SQLException("Unknown nationality/country: " + nationality);
    }
}