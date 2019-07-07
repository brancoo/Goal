package com.example.Models.Competition;

import com.example.Models.Team.Winner;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CompetitionSeason implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("startDate")
    private String startDate;   //start date of the season

    @SerializedName("endDate")
    private String endDate;     //final date of the season

    @SerializedName("currentMatchday")
    private String currentMatchday;

    @SerializedName("winner")
    private Winner winner;

    public CompetitionSeason(String id, String startDate, String endDate, String currentMatchday, Winner winner) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.currentMatchday = currentMatchday;
        this.winner = winner;
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

    public Winner getWinner() { return winner; }

    @Override
    public String toString() {
        return "startDate = " + startDate + '\t' + "endDate = " + endDate + '\t' +
                "currentMatchday =" + currentMatchday + '\n';
    }
}
