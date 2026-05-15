package com.f1manager.service;

import com.f1manager.dao.PilotoDAO;
import com.f1manager.model.Piloto;
import java.sql.SQLException;
import java.util.List;

public class PilotoService {

    private final PilotoDAO pilotoDAO = new PilotoDAO();

    public List<Piloto> getAllPilotos() throws SQLException {
        return pilotoDAO.findAll();
    }

    public Piloto getPilotoById(String id) throws SQLException {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty");
        }
        return pilotoDAO.findById(id);
    }
}