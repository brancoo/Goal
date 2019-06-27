package com.example.Models.Match;

import com.example.Models.Competition.Competition;
import com.example.Models.Competition.CompetitionSeason;
import com.example.Models.Referee.Referee;
import com.example.Models.Team.Team;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Match {
    @SerializedName("id")
    private String id;

    @SerializedName("competition")
    private Competition competition;

    @SerializedName("season")
    private CompetitionSeason season;

    @SerializedName("utcDate")
    private String utcDate;

    @SerializedName("status")
    private String status;

    @SerializedName("venue")
    private String venue;

    @SerializedName("matchday")
    private String matchday;

    @SerializedName("stage")
    private String stage;

    @SerializedName("group")
    private String group;

    @SerializedName("homeTeam")
    private Team homeTeam;

    @SerializedName("awayTeam")
    private Team awayTeam;

    @SerializedName("score")
    private MatchScore score;

    @SerializedName("referees")
    private List<Referee> referees;

    public Match(String id, Competition competition, CompetitionSeason season, String utcDate, String status, String venue, String matchday, String stage, String group, Team homeTeam, Team awayTeam, MatchScore score, List<Referee> referees) {
        this.id = id;
        this.competition = competition;
        this.season = season;
        this.utcDate = utcDate;
        this.status = status;
        this.venue = venue;
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

    public Competition getCompetition() {
        return competition;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public MatchScore getScore() {
        return score;
    }

    public String getUtcDate() {
        return utcDate;
    }


    @Override
    public String toString() {
        return "Match{" +
                "id='" + id + '\'' +
                ", competition=" + competition +
                ", season=" + season +
                ", utcDate='" + utcDate + '\'' +
                ", status='" + status + '\'' +
                ", venue='" + venue + '\'' +
                ", matchday='" + matchday + '\'' +
                ", stage='" + stage + '\'' +
                ", group='" + group + '\'' + '\'' +
                ", homeTeam=" + homeTeam.getName() +
                ", awayTeam=" + awayTeam.getName() +
                ", score=" + score +
                ", referees=" + referees +
                '}';
    }

}
