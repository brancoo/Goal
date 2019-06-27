package com.example.Models.Standing;


import com.example.Models.Competition.Competition;
import com.example.Models.Competition.CompetitionSeason;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Standing {
    @SerializedName("competition")
    private Competition competition;

    @SerializedName("season")
    private CompetitionSeason season;

    @SerializedName("standings")
    private List<StandingType> standings;

    public Standing(Competition competition, CompetitionSeason season, List<StandingType> standings) {
        this.competition = competition;
        this.season = season;
        this.standings = standings;
    }

    public Competition getCompetition() { return competition;}
    public CompetitionSeason getCompetitionSeason() { return season;}
    public List<StandingType> getStandings() { return standings;}
}
