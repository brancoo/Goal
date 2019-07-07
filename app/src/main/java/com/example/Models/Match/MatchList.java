package com.example.Models.Match;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MatchList implements Serializable {
    @SerializedName("matches")
    private List<Match> matches;

    public MatchList(List<Match> matches) {
        this.matches = matches;
    }

    public List<Match> getMatches() {
        return matches;
    }
}
