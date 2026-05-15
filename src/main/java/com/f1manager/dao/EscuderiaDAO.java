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
        String sql = "INSERT INTO constructor (id, name, full_name, country_id, total_points, total_race_wins, total_podiums, total_championship_wins) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, escuderia.getId());
            stmt.setString(2, escuderia.getName());
            stmt.setString(3, escuderia.getFullName());
            stmt.setString(4, escuderia.getNationality());
            if (escuderia.getTotalPoints() != null) {
                stmt.setDouble(5, escuderia.getTotalPoints());
            } else {
                stmt.setNull(5, Types.DECIMAL);
            }
            if (escuderia.getTotalRaceWins() != null) {
                stmt.setInt(6, escuderia.getTotalRaceWins());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }
            if (escuderia.getTotalPodiums() != null) {
                stmt.setInt(7, escuderia.getTotalPodiums());
            } else {
                stmt.setNull(7, Types.INTEGER);
            }
            if (escuderia.getTotalChampionshipWins() != null) {
                stmt.setInt(8, escuderia.getTotalChampionshipWins());
            } else {
                stmt.setNull(8, Types.INTEGER);
            }

            stmt.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM constructor WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }

    public void update(Escuderia escuderia) throws SQLException {
        String sql = "UPDATE constructor SET name = ?, full_name = ?, country_id = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, escuderia.getName());
            stmt.setString(2, escuderia.getFullName());
            stmt.setString(3, escuderia.getNationality());
            stmt.setString(4, escuderia.getId());

            stmt.executeUpdate();
        }
    }
}