package com.f1manager.model;

public class Escuderia {

    private String id;
    private String name;
    private String fullName;
    private String nationality;
    private Double totalPoints;
    private Integer totalRaceWins;
    private Integer totalPodiums;
    private Integer totalChampionshipWins;

    public Escuderia() {}

    public Escuderia(String id, String name, String fullName, String nationality,
                     Double totalPoints, Integer totalRaceWins,
                     Integer totalPodiums, Integer totalChampionshipWins) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.nationality = nationality;
        this.totalPoints = totalPoints;
        this.totalRaceWins = totalRaceWins;
        this.totalPodiums = totalPodiums;
        this.totalChampionshipWins = totalChampionshipWins;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    public Double getTotalPoints() { return totalPoints; }
    public void setTotalPoints(Double totalPoints) { this.totalPoints = totalPoints; }

    public Integer getTotalRaceWins() { return totalRaceWins; }
    public void setTotalRaceWins(Integer totalRaceWins) { this.totalRaceWins = totalRaceWins; }

    public Integer getTotalPodiums() { return totalPodiums; }
    public void setTotalPodiums(Integer totalPodiums) { this.totalPodiums = totalPodiums; }

    public Integer getTotalChampionshipWins() { return totalChampionshipWins; }
    public void setTotalChampionshipWins(Integer totalChampionshipWins) { this.totalChampionshipWins = totalChampionshipWins; }
}