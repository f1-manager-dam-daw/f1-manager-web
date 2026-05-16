package com.f1manager.model;

public class RaceResult {
    private int raceId;
    private String driverId;
    private String driverName;
    private String driverCode;
    private String constructorId;
    private String constructorName;
    private String constructorFullName;
    private Integer grid;
    private Integer position;
    private String positionText;
    private Double points;
    private Integer laps;
    private String status;

    public RaceResult() {}

    public RaceResult(int raceId, String driverId, String driverName, String driverCode, String constructorId, String constructorName, String constructorFullName, Integer grid, Integer position, String positionText, Double points, Integer laps, String status) {
        this.raceId = raceId;
        this.driverId = driverId;
        this.driverName = driverName;
        this.driverCode = driverCode;
        this.constructorId = constructorId;
        this.constructorName = constructorName;
        this.constructorFullName = constructorFullName;
        this.grid = grid;
        this.position = position;
        this.positionText = positionText;
        this.points = points;
        this.laps = laps;
        this.status = status;
    }

    public int getRaceId() { return raceId; }
    public void setRaceId(int raceId) { this.raceId = raceId; }

    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }

    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }

    public String getDriverCode() { return driverCode; }
    public void setDriverCode(String driverCode) { this.driverCode = driverCode; }

    public String getConstructorId() { return constructorId; }
    public void setConstructorId(String constructorId) { this.constructorId = constructorId; }

    public String getConstructorName() { return constructorName; }
    public void setConstructorName(String constructorName) { this.constructorName = constructorName; }

    public String getConstructorFullName() { return constructorFullName; }
    public void setConstructorFullName(String constructorFullName) { this.constructorFullName = constructorFullName; }

    public Integer getGrid() { return grid; }
    public void setGrid(Integer grid) { this.grid = grid; }

    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }

    public String getPositionText() { return positionText; }
    public void setPositionText(String positionText) { this.positionText = positionText; }

    public Double getPoints() { return points; }
    public void setPoints(Double points) { this.points = points; }

    public Integer getLaps() { return laps; }
    public void setLaps(Integer laps) { this.laps = laps; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
