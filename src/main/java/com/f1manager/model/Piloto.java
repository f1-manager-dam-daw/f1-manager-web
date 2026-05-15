package com.f1manager.model;

public class Piloto {

    private String id;
    private String forename;
    private String surname;
    private String code;
    private Integer number;
    private String nationality;
    private String dateOfBirth;
    private Double totalPoints;
    private Integer totalRaceWins;
    private Integer totalPodiums;

    public Piloto() {}

    public Piloto(String id, String forename, String surname, String code,
                  Integer number, String nationality, String dateOfBirth,
                  Double totalPoints, Integer totalRaceWins, Integer totalPodiums) {
        this.id = id;
        this.forename = forename;
        this.surname = surname;
        this.code = code;
        this.number = number;
        this.nationality = nationality;
        this.dateOfBirth = dateOfBirth;
        this.totalPoints = totalPoints;
        this.totalRaceWins = totalRaceWins;
        this.totalPodiums = totalPodiums;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getForename() { return forename; }
    public void setForename(String forename) { this.forename = forename; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public Double getTotalPoints() { return totalPoints; }
    public void setTotalPoints(Double totalPoints) { this.totalPoints = totalPoints; }

    public Integer getTotalRaceWins() { return totalRaceWins; }
    public void setTotalRaceWins(Integer totalRaceWins) { this.totalRaceWins = totalRaceWins; }

    public Integer getTotalPodiums() { return totalPodiums; }
    public void setTotalPodiums(Integer totalPodiums) { this.totalPodiums = totalPodiums; }
}