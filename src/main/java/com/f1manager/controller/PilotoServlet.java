package com.f1manager.controller;

import com.f1manager.dao.PilotoDAO;
import com.f1manager.model.Piloto;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/api/drivers")
public class PilotoServlet extends HttpServlet {

    private final PilotoDAO pilotoDAO = new PilotoDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCorsHeaders(resp);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        String constructorId = req.getParameter("constructorId");
        PrintWriter out = resp.getWriter();

        try {
            if (constructorId != null && !constructorId.isEmpty()) {
                List<Piloto> pilotos = pilotoDAO.findByConstructorId(constructorId);
                out.print(gson.toJson(pilotos));
            } else if (id != null && !id.isEmpty()) {
                Piloto p = pilotoDAO.findById(id);
                if (p != null) {
                    out.print(gson.toJson(p));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\": \"Driver not found\"}");
                }
            } else {
                List<Piloto> pilotos = pilotoDAO.findAll();
                out.print(gson.toJson(pilotos));
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"" + e.getMessage() + "\"}");
        }
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCorsHeaders(resp);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            Piloto p = gson.fromJson(req.getReader(), Piloto.class);
            
            if (p.getId() == null || p.getId().isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\": \"Missing driver id\"}");
                return;
            }

            pilotoDAO.save(p);
            
            resp.setStatus(HttpServletResponse.SC_CREATED);
            out.print("{\"message\": \"Driver created successfully\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"" + e.getMessage() + "\"}");
        }
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCorsHeaders(resp);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String id = req.getParameter("id");
        if (id == null || id.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"Missing driver id\"}");
            return;
        }

        try {
            pilotoDAO.delete(id);
            resp.setStatus(HttpServletResponse.SC_OK);
            out.print("{\"message\": \"Driver deleted successfully\"}");
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            String message = "Database error";
            if (e.getMessage() != null && e.getMessage().contains("foreign key constraint fails")) {
                message = "Cannot delete this driver because they have associated race data. Please delete the related data first.";
            } else {
                message = e.getMessage();
            }
            out.print("{\"error\": \"" + message + "\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"" + e.getMessage() + "\"}");
        }
        out.flush();
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCorsHeaders(resp);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            Piloto p = gson.fromJson(req.getReader(), Piloto.class);
            if (p.getId() == null || p.getId().isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\": \"Missing driver id\"}");
                return;
            }

            pilotoDAO.update(p);
            resp.setStatus(HttpServletResponse.SC_OK);
            out.print("{\"message\": \"Driver updated successfully\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"" + e.getMessage() + "\"}");
        }
        out.flush();
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCorsHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void setCorsHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }
}
