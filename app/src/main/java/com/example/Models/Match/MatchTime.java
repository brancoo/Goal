package com.example.Models.Match;

import com.google.gson.annotations.SerializedName;

public class MatchTime {
    @SerializedName("homeTean")
    private String homeTeam;

    @SerializedName("awayTeam")
    private String awayTeam;

    public MatchTime(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

}
