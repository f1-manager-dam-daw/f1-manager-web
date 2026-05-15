package com.f1manager.service;

import com.f1manager.dao.EscuderiaDAO;
import com.f1manager.model.Escuderia;
import java.sql.SQLException;
import java.util.List;

public class EscuderiaService {

    private final EscuderiaDAO escuderiaDAO = new EscuderiaDAO();

    public List<Escuderia> getAllEscuderias() throws SQLException {
        return escuderiaDAO.findAll();
    }

    public Escuderia getEscuderiaById(String id) throws SQLException {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty");
        }
        return escuderiaDAO.findById(id);
    }
}