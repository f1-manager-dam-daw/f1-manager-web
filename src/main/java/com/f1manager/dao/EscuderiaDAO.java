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
}