package com.f1manager.servlet;

import com.f1manager.model.Piloto;
import com.f1manager.service.PilotoService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/api/drivers/*")
public class PilotoServlet extends HttpServlet {

    private final PilotoService pilotoService = new PilotoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out = resp.getWriter();
        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /api/drivers → lista todos
                List<Piloto> pilotos = pilotoService.getAllPilotos();
                out.print(toJsonList(pilotos));
            } else {
                // GET /api/drivers/{id} → detalle de uno
                String id = pathInfo.substring(1);
                Piloto p = pilotoService.getPilotoById(id);
                if (p == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\":\"Driver not found\"}");
                } else {
                    out.print(toJson(p));
                }
            }
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"Database error: " + e.getMessage() + "\"}");
        }
        out.flush();
    }

    private String toJson(Piloto p) {
        return "{"
                + "\"id\":\"" + p.getId() + "\","
                + "\"forename\":\"" + p.getForename() + "\","
                + "\"surname\":\"" + p.getSurname() + "\","
                + "\"code\":\"" + p.getCode() + "\","
                + "\"number\":" + p.getNumber() + ","
                + "\"nationality\":\"" + p.getNationality() + "\","
                + "\"dateOfBirth\":\"" + p.getDateOfBirth() + "\","
                + "\"totalPoints\":" + p.getTotalPoints() + ","
                + "\"totalRaceWins\":" + p.getTotalRaceWins() + ","
                + "\"totalPodiums\":" + p.getTotalPodiums()
                + "}";
    }

    private String toJsonList(List<Piloto> pilotos) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < pilotos.size(); i++) {
            sb.append(toJson(pilotos.get(i)));
            if (i < pilotos.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
}