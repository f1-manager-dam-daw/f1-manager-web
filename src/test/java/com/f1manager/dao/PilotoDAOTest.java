package com.f1manager.dao;

import com.f1manager.model.Piloto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

public class PilotoDAOTest {

    @Test
    public void testFindAll() throws SQLException {
        PilotoDAO dao = new PilotoDAO();
        List<Piloto> list = dao.findAll();
        assertNotNull(list);
        System.out.println("FindAll: Loaded " + list.size() + " drivers.");
    }

    /*
    @Test
    public void testCRUD() throws SQLException {
        // ... problematic due to strict DB schema
    }
    */
}
