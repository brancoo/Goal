package com.example.Models.Team;

import com.example.Models.Competition.Competition;
import com.example.Models.Competition.CompetitionSeason;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamList {
    @SerializedName("competition")
    private Competition competition;

    @SerializedName("season")
    private CompetitionSeason season;

    @SerializedName("teams")
    private List<Team> teams;

    public TeamList(Competition competition, CompetitionSeason season, List<Team> teams) {
        this.competition = competition;
        this.season = season;
        this.teams = teams;
    }

    public List<Team> getTeams() {
        return teams;
    }
    public Competition getCompetition() { return competition; }
    public CompetitionSeason getSeason() { return season; }

}
