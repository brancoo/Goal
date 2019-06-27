package com.example.Models.Team;

import com.example.Models.Area.Area;
import com.example.Models.Competition.Competition;
import com.example.Models.Player.Player;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Team {
    @SerializedName("id")
    private String id;

    @SerializedName("area")
    private Area area;

    @SerializedName("name")
    private String name;

    @SerializedName("alias")
    private String alias; //team's nickname

    @SerializedName("email")
    private String email;

    @SerializedName("address")
    private String address;

    @SerializedName("squad")
    private List<Player> squad; //list of team's players

    @SerializedName("competitions")
    private List<Competition> competitions; //list of team's competitions

    @SerializedName("urlImage")
    private String urlImage;

    @SerializedName("website")
    private String website;

    @SerializedName("teamColours")
    private String teamColours; //list of team's colours

    public Team(String id, Area area, String name, String alias, String email, String address, List<Player> squad, List<Competition> competitions, String teamColours, String urlImage, String website) {
        this.id = id;
        this.area = area;
        this.name = name;
        this.alias = alias;
        this.email = email;
        this.address = address;
        this.squad = squad;
        this.competitions = competitions;
        this.teamColours = teamColours;
        this.urlImage = urlImage;
        this.website = website;
    }

    public Area getArea() { return area; }

    public String getUrlImage() { return urlImage; }

    public String getWebsite() { return website;}

    public String getName() { return name; }

    public String getId() { return id; }

    public String getAlias() { return alias; }

    public String getEmail() { return email; }

    public String getAddress() { return address; }

    public List<Player> getSquad() { return squad; }

    public List<Competition> getCompetitions() { return competitions; }

    public String getTeamColours() { return teamColours; }
}
