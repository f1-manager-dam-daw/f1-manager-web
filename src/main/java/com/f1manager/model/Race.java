package com.f1manager.model;

public class Race {
    private int id;
    private int year;
    private int round;
    private String name;
    private String officialName;
    private String date;
    private String circuitName;
    private Integer laps;
    private Double distance;
    private boolean driversChampionshipDecider;
    private boolean constructorsChampionshipDecider;

    public Race() {}

    public Race(int id, int year, int round, String name, String officialName, String date, String circuitName, Integer laps, Double distance, boolean driversChampionshipDecider, boolean constructorsChampionshipDecider) {
        this.id = id;
        this.year = year;
        this.round = round;
        this.name = name;
        this.officialName = officialName;
        this.date = date;
        this.circuitName = circuitName;
        this.laps = laps;
        this.distance = distance;
        this.driversChampionshipDecider = driversChampionshipDecider;
        this.constructorsChampionshipDecider = constructorsChampionshipDecider;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getRound() { return round; }
    public void setRound(int round) { this.round = round; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getOfficialName() { return officialName; }
    public void setOfficialName(String officialName) { this.officialName = officialName; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getCircuitName() { return circuitName; }
    public void setCircuitName(String circuitName) { this.circuitName = circuitName; }

    public Integer getLaps() { return laps; }
    public void setLaps(Integer laps) { this.laps = laps; }

    public Double getDistance() { return distance; }
    public void setDistance(Double distance) { this.distance = distance; }

    public boolean isDriversChampionshipDecider() { return driversChampionshipDecider; }
    public void setDriversChampionshipDecider(boolean driversChampionshipDecider) { this.driversChampionshipDecider = driversChampionshipDecider; }

    public boolean isConstructorsChampionshipDecider() { return constructorsChampionshipDecider; }
    public void setConstructorsChampionshipDecider(boolean constructorsChampionshipDecider) { this.constructorsChampionshipDecider = constructorsChampionshipDecider; }
}
