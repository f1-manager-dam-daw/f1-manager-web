package com.f1manager.controller;

import com.f1manager.dao.PilotoDAO;
import com.f1manager.model.Piloto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/api/drivers")
public class PilotoServlet extends HttpServlet {

    private final PilotoDAO pilotoDAO = new PilotoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCorsHeaders(resp);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        PrintWriter out = resp.getWriter();

        try {
            if (id != null && !id.isEmpty()) {
                Piloto p = pilotoDAO.findById(id);
                if (p != null) {
                    out.print(pilotoToJson(p));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\": \"Driver not found\"}");
                }
            } else {
                List<Piloto> pilotos = pilotoDAO.findAll();
                out.print(pilotosToJson(pilotos));
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
            // Very simple JSON parsing for 1st year project
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = req.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String body = buffer.toString();
            
            // Assuming simple key-value pairs in JSON like {"id":"...", "forename":"..."}
            Piloto p = new Piloto();
            p.setId(extractJsonString(body, "id"));
            p.setForename(extractJsonString(body, "forename"));
            p.setSurname(extractJsonString(body, "surname"));
            p.setCode(extractJsonString(body, "code"));
            p.setNationality(extractJsonString(body, "nationality"));
            
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
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    private String extractJsonString(String json, String key) {
        String search = "\"" + key + "\":\"";
        int start = json.indexOf(search);
        if (start == -1) return "";
        start += search.length();
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }

    private String pilotosToJson(List<Piloto> pilotos) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < pilotos.size(); i++) {
            sb.append(pilotoToJson(pilotos.get(i)));
            if (i < pilotos.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private String pilotoToJson(Piloto p) {
        return String.format(
            "{\"id\":\"%s\",\"forename\":\"%s\",\"surname\":\"%s\",\"code\":\"%s\",\"number\":%s,\"nationality\":\"%s\",\"dateOfBirth\":\"%s\",\"totalPoints\":%s,\"totalRaceWins\":%s,\"totalPodiums\":%s}",
            p.getId(), p.getForename(), p.getSurname(), p.getCode(), 
            p.getNumber() == null ? "null" : p.getNumber(),
            p.getNationality(), p.getDateOfBirth(), 
            p.getTotalPoints() == null ? "null" : p.getTotalPoints(), 
            p.getTotalRaceWins() == null ? "null" : p.getTotalRaceWins(), 
            p.getTotalPodiums() == null ? "null" : p.getTotalPodiums()
        );
    }
}
