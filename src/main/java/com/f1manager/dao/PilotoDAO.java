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
}