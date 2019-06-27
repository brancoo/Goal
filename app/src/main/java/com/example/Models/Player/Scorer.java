package com.example.Models.Player;

import com.example.Models.Team.Team;
import com.google.gson.annotations.SerializedName;

public class Scorer {
    @SerializedName("player")
    private Player player;

    @SerializedName("team")
    private Team team;

    @SerializedName("numberOfGoals")
    private String numberOfGoals;

    public Scorer(Player player, Team team, String numberOfGoals) {
        this.player = player;
        this.team = team;
        this.numberOfGoals = numberOfGoals;
    }

    public Player getPlayer() {
        return player;
    }

    public Team getTeam() {
        return team;
    }

    public String getNumberOfGoals() {
        return numberOfGoals;
    }
}
