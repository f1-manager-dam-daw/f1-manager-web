package com.f1manager.controller;

import com.f1manager.dao.EscuderiaDAO;
import com.f1manager.model.Escuderia;
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

@WebServlet("/api/constructors")
public class EscuderiaServlet extends HttpServlet {

    private final EscuderiaDAO escuderiaDAO = new EscuderiaDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCorsHeaders(resp);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        PrintWriter out = resp.getWriter();

        try {
            if (id != null && !id.isEmpty()) {
                Escuderia e = escuderiaDAO.findById(id);
                if (e != null) {
                    out.print(gson.toJson(e));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\": \"Constructor not found\"}");
                }
            } else {
                List<Escuderia> escuderias = escuderiaDAO.findAll();
                out.print(gson.toJson(escuderias));
            }
        } catch (SQLException e) {
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
            Escuderia e = gson.fromJson(req.getReader(), Escuderia.class);
            if (e.getId() == null || e.getId().isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\": \"Missing constructor id\"}");
                return;
            }
            escuderiaDAO.save(e);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            out.print("{\"message\": \"Constructor created successfully\"}");
        } catch (Exception ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"" + ex.getMessage() + "\"}");
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
            out.print("{\"error\": \"Missing constructor id\"}");
            return;
        }

        try {
            escuderiaDAO.delete(id);
            resp.setStatus(HttpServletResponse.SC_OK);
            out.print("{\"message\": \"Constructor deleted successfully\"}");
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            String message = "Database error";
            if (e.getMessage().contains("foreign key constraint fails")) {
                message = "Cannot delete this constructor because it has associated data (races or results).";
            } else {
                message = e.getMessage();
            }
            out.print("{\"error\": \"" + message + "\"}");
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
            Escuderia e = gson.fromJson(req.getReader(), Escuderia.class);
            if (e.getId() == null || e.getId().isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\": \"Missing constructor id\"}");
                return;
            }

            escuderiaDAO.update(e);
            resp.setStatus(HttpServletResponse.SC_OK);
            out.print("{\"message\": \"Constructor updated successfully\"}");
        } catch (Exception ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"" + ex.getMessage() + "\"}");
        }
        out.flush();
    }

    private void setCorsHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCorsHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
