package com.example.Models.Player;

import com.example.Models.Competition.Competition;
import com.example.Models.Competition.CompetitionSeason;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScoringList {
    @SerializedName("competition")
    private Competition competition;

    @SerializedName("competitionSeason")
    private CompetitionSeason competitionSeason;

    @SerializedName("scorers")
    private List<Scorer> scorers;

    public ScoringList(Competition competition, CompetitionSeason competitionSeason, List<Scorer> scorers) {
        this.competition = competition;
        this.competitionSeason = competitionSeason;
        this.scorers = scorers;
    }

    public Competition getCompetition() {
        return competition;
    }

    public CompetitionSeason getCompetitionSeason() {
        return competitionSeason;
    }

    public List<Scorer> getScorers() {
        return scorers;
    }
}
