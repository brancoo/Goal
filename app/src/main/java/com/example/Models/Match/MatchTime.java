package com.example.Models.Match;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MatchTime implements Serializable {
    @SerializedName("homeTeam")
    private String homeTeam;

    @SerializedName("awayTeam")
    private String awayTeam;

    public MatchTime(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public String getHomeTeam() { return homeTeam; }

    public String getAwayTeam() {
        return awayTeam;
    }

}
