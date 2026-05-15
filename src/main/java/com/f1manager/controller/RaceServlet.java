package com.f1manager.controller;

import com.f1manager.dao.RaceDAO;
import com.f1manager.dao.RaceResultDAO;
import com.f1manager.model.Race;
import com.f1manager.model.RaceResult;
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

@WebServlet("/api/races/*")
public class RaceServlet extends HttpServlet {

    private final RaceDAO raceDAO = new RaceDAO();
    private final RaceResultDAO resultDAO = new RaceResultDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCorsHeaders(resp);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // List all or filter by year
                String yearParam = req.getParameter("year");
                if (yearParam != null) {
                    List<Race> races = raceDAO.findByYear(Integer.parseInt(yearParam));
                    out.print(gson.toJson(races));
                } else {
                    List<Race> races = raceDAO.findAll();
                    out.print(gson.toJson(races));
                }
            } else {
                String[] parts = pathInfo.split("/");
                // parts[0] is empty, parts[1] is ID
                int id = Integer.parseInt(parts[1]);

                if (parts.length > 2 && parts[2].equals("results")) {
                    // results for race ID
                    List<RaceResult> results = resultDAO.findByRaceId(id);
                    out.print(gson.toJson(results));
                } else {
                    // detail for race ID
                    Race race = raceDAO.findById(id);
                    if (race != null) {
                        out.print(gson.toJson(race));
                    } else {
                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        out.print("{\"error\": \"Race not found\"}");
                    }
                }
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"" + e.getMessage() + "\"}");
        }
        out.flush();
    }

    private void setCorsHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCorsHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
