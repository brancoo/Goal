package com.example.Models.Match;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MatchList {
    @SerializedName("matches")
    private List<Match> matches;

    public MatchList(List<Match> matches) {
        this.matches = matches;
    }

    public List<Match> getMatches() {
        return matches;
    }
}
