package com.example.Models.Match;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TeamRecord implements Serializable {
    @SerializedName("wins")
    private int wins;

    @SerializedName("draws")
    private int draws;

    @SerializedName("losses")
    private int losses;

    public TeamRecord(int wins, int draws, int losses) {
        this.wins = wins;
        this.draws = draws;
        this.losses = losses;
    }

    public int getWins() { return wins; }

    public int getDraws() {
        return draws;
    }

    public int getLosses() {
        return losses;
    }
}
