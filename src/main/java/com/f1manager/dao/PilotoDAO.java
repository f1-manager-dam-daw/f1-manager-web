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

    public void save(Piloto piloto) throws SQLException {
        String sql = "INSERT INTO driver (id, name, first_name, last_name, full_name, abbreviation, permanent_number, date_of_birth, nationality_country_id, gender, place_of_birth, country_of_birth_country_id, total_championship_wins, total_race_wins, total_podiums, total_points) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String fullName = piloto.getForename() + " " + piloto.getSurname();
            stmt.setString(1, piloto.getId());
            stmt.setString(2, fullName);
            stmt.setString(3, piloto.getForename());
            stmt.setString(4, piloto.getSurname());
            stmt.setString(5, fullName);
            stmt.setString(6, piloto.getCode());
            if (piloto.getNumber() != null) {
                stmt.setString(7, String.valueOf(piloto.getNumber()));
            } else {
                stmt.setNull(7, Types.VARCHAR);
            }
            if (piloto.getDateOfBirth() != null && !piloto.getDateOfBirth().isEmpty()) {
                stmt.setDate(8, java.sql.Date.valueOf(piloto.getDateOfBirth()));
            } else {
                stmt.setNull(8, Types.DATE);
            }
            stmt.setString(9, piloto.getNationality());
            stmt.setString(10, "M"); // Default gender
            stmt.setString(11, "Unknown"); // Default place of birth
            stmt.setString(12, piloto.getNationality()); // Default country of birth
            stmt.setInt(13, 0); // total_championship_wins
            stmt.setInt(14, 0); // total_race_wins
            stmt.setInt(15, 0); // total_podiums
            stmt.setDouble(16, 0.0); // total_points

            stmt.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM driver WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }
    public void update(Piloto piloto) throws SQLException {
        String sql = "UPDATE driver SET first_name = ?, last_name = ?, name = ?, full_name = ?, abbreviation = ?, nationality_country_id = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String fullName = piloto.getForename() + " " + piloto.getSurname();
            stmt.setString(1, piloto.getForename());
            stmt.setString(2, piloto.getSurname());
            stmt.setString(3, fullName);
            stmt.setString(4, fullName);
            stmt.setString(5, piloto.getCode());
            stmt.setString(6, piloto.getNationality());
            stmt.setString(7, piloto.getId());

            stmt.executeUpdate();
        }
    }
}