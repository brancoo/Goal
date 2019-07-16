package com.example.Models.Match;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Head2Head implements Serializable {
    @SerializedName("numberOfMatches")
    private String numberOfMatches;

    @SerializedName("totalGoals")
    private String totalGoals;

    @SerializedName("homeTeam")
    private TeamRecord homeTeam;

    @SerializedName("awayTeam")
    private TeamRecord awayTeam;

    public Head2Head(String numberOfMatches, String totalGoals, TeamRecord homeTeam, TeamRecord awayTeam) {
        this.numberOfMatches = numberOfMatches;
        this.totalGoals = totalGoals;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public String getNumberOfMatches() {
        return numberOfMatches;
    }

    public String getTotalGoals() {
        return totalGoals;
    }

    public TeamRecord getHomeTeam() {
        return homeTeam;
    }

    public TeamRecord getAwayTeam() {
        return awayTeam;
    }
}
