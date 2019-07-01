package com.example.Models.Standing;


import com.example.Models.Team.Team;
import com.google.gson.annotations.SerializedName;

public class StandingTeam {
    @SerializedName("team")
    private Team team;

    @SerializedName("position")
    private String position;

    @SerializedName("countryOfBirth")
    private String playedGames;

    @SerializedName("won")
    private String won;

    @SerializedName("draw")
    private String draw;

    @SerializedName("lost")
    private String lost;

    @SerializedName("points")
    private String points;

    @SerializedName("goalsFor")
    private String goalsFor;

    @SerializedName("goalsAgainst")
    private String goalsAgainst;

    @SerializedName("goalDifference")
    private String goalDifference;

    public StandingTeam(Team team, String position, String playedGames, String won, String draw, String lost, String points, String goalsFor, String goalsAgainst, String goalDifference) {
        this.position = position;
        this.team = team;
        this.playedGames = playedGames;
        this.won = won;
        this.draw = draw;
        this.lost = lost;
        this.points = points;
        this.goalsFor = goalsFor;
        this.goalsAgainst = goalsAgainst;
        this.goalDifference = goalDifference;
    }

    public String getPlayedGames() {
        return playedGames;
    }

    public String getWon() {
        return won;
    }

    public String getDraw() {
        return draw;
    }

    public String getLost() {
        return lost;
    }

    public String getPoints() {
        return points;
    }

    public String getGoalsFor() {
        return goalsFor;
    }

    public String getPosition() { return position; }

    public String getGoalsAgainst() {
        return goalsAgainst;
    }

    public String getGoalDifference() {
        return goalDifference;
    }

    public Team getTeam() { return team;}

    @Override
    public String toString() {
        return team.getName() + "\t\t\t\t" + getPosition()+ "\t\t\t\t" + getPoints() + '\t' + getWon() + '\t' + getDraw()
                + '\t' + getLost() + '\t' + '\t' + getGoalsFor()
                + '\t' + getGoalsAgainst() + '\t' + getGoalDifference();
    }
}
