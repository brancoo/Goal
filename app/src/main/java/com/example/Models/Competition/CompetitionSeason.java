package com.example.Models.Competition;

import com.google.gson.annotations.SerializedName;

public class CompetitionSeason {
    @SerializedName("id")
    private String id;

    @SerializedName("startDate")
    private String startDate;   //start date of the season

    @SerializedName("endDate")
    private String endDate;     //final date of the season

    @SerializedName("currentMatchday")
    private String currentMatchday;

    public CompetitionSeason(String id, String startDate, String endDate, String currentMatchday) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.currentMatchday = currentMatchday;
    }

    public String getId() {
        return id;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getCurrentMatchday() {
        return currentMatchday;
    }

    @Override
    public String toString() {
        return "CompetitionSeason{" +
                "id='" + id + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", currentMatchday='" + currentMatchday + '\'' +
                '}';
    }
}
