package com.f1manager.dao;

import com.f1manager.model.RaceResult;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RaceResultDAO {

    public List<RaceResult> findByRaceId(int raceId) throws SQLException {
        List<RaceResult> results = new ArrayList<>();
        String sql = "SELECT * FROM app_results WHERE race_id = ? ORDER BY position ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, raceId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                results.add(new RaceResult(
                    rs.getInt("race_id"),
                    rs.getString("driver_id"),
                    rs.getString("constructor_id"),
                    rs.getInt("grid"),
                    rs.getInt("position"),
                    rs.getString("position_text"),
                    rs.getDouble("points"),
                    rs.getInt("laps"),
                    rs.getString("status")
                ));
            }
        }
        return results;
    }
}
