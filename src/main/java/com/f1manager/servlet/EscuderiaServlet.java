package com.f1manager.servlet;

import com.f1manager.model.Escuderia;
import com.f1manager.service.EscuderiaService;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;


public class EscuderiaServlet extends HttpServlet {

    private final EscuderiaService escuderiaService = new EscuderiaService();

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
                // GET /api/teams → lista todas
                List<Escuderia> escuderias = escuderiaService.getAllEscuderias();
                out.print(toJsonList(escuderias));
            } else {
                // GET /api/teams/{id} → detalle de una
                String id = pathInfo.substring(1);
                Escuderia e = escuderiaService.getEscuderiaById(id);
                if (e == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\":\"Team not found\"}");
                } else {
                    out.print(toJson(e));
                }
            }
        } catch (SQLException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"Database error: " + ex.getMessage() + "\"}");
        }
        out.flush();
    }

    private String toJson(Escuderia e) {
        return "{"
                + "\"id\":\"" + e.getId() + "\","
                + "\"name\":\"" + e.getName() + "\","
                + "\"fullName\":\"" + e.getFullName() + "\","
                + "\"nationality\":\"" + e.getNationality() + "\","
                + "\"totalPoints\":" + e.getTotalPoints() + ","
                + "\"totalRaceWins\":" + e.getTotalRaceWins() + ","
                + "\"totalPodiums\":" + e.getTotalPodiums() + ","
                + "\"totalChampionshipWins\":" + e.getTotalChampionshipWins()
                + "}";
    }

    private String toJsonList(List<Escuderia> escuderias) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < escuderias.size(); i++) {
            sb.append(toJson(escuderias.get(i)));
            if (i < escuderias.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
}