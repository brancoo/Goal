package com.example.Models.Match;

import com.example.Models.Competition.CompetitionSeason;
import com.example.Models.Referee.Referee;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Match implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("season")
    private CompetitionSeason season;

    @SerializedName("utcDate")
    private String utcDate;

    @SerializedName("status")
    private String status;

    @SerializedName("matchday")
    private String matchday;

    @SerializedName("stage")
    private String stage;

    @SerializedName("group")
    private String group;

    @SerializedName("score")
    private MatchScore score;

    @SerializedName("homeTeam")
    private MatchTeam homeTeam;

    @SerializedName("awayTeam")
    private MatchTeam awayTeam;

    @SerializedName("referees")
    private List<Referee> referees;

    public Match(String id, CompetitionSeason season, String utcDate, String status, String matchday, String stage, String group, MatchTeam homeTeam, MatchTeam awayTeam, MatchScore score, List<Referee> referees) {
        this.id = id;
        this.season = season;
        this.utcDate = utcDate;
        this.status = status;
        this.matchday = matchday;
        this.stage = stage;
        this.group = group;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.score = score;
        this.referees = referees;
    }

    public CompetitionSeason getSeason() {
        return season;
    }

    public String getStatus() {
        return status;
    }

    public String getMatchday() {
        return matchday;
    }

    public String getStage() {
        return stage;
    }

    public String getGroup() {
        return group;
    }

    public List<Referee> getReferees() {
        return referees;
    }

    public MatchTeam getHomeTeam() {
        return homeTeam;
    }

    public MatchTeam getAwayTeam() {
        return awayTeam;
    }

    public MatchScore getScore() {
        return score;
    }

    public String getUtcDate() {
        return utcDate;
    }
}
