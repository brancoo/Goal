package com.example.Models.Match;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MatchScore implements Serializable {
    @SerializedName("winner")
    private String winner;

    @SerializedName("duration")
    private String duration;

    @SerializedName("fullTime")
    private MatchTime fullTime;

    @SerializedName("halfTime")
    private MatchTime halfTime;

    @SerializedName("extraTime")
    private MatchTime extraTime;

    @SerializedName("penalties")
    private MatchTime penalties;

    public MatchScore(String winner, String duration, MatchTime fullTime, MatchTime halfTime, MatchTime extraTime, MatchTime penalties) {
        this.winner = winner;
        this.duration = duration;
        this.fullTime = fullTime;
        this.halfTime = halfTime;
        this.extraTime = extraTime;
        this.penalties = penalties;
    }

    public String getWinner() {
        return winner;
    }

    public String getDuration() {
        return duration;
    }

    public MatchTime getHalfTime() {
        return halfTime;
    }

    public MatchTime getExtraTime() {
        return extraTime;
    }

    public MatchTime getPenalties() {
        return penalties;
    }

    public MatchTime getFullTime() {
        return fullTime;
    }
}
