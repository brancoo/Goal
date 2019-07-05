package com.example.Models.Team;

import com.example.Models.Area.Area;
import com.example.Models.Competition.Competition;
import com.example.Models.Player.Player;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Team implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("area")
    private Area area;

    @SerializedName("name")
    private String name;

    @SerializedName("tla")
    private String tla; //team's nickname

    @SerializedName("email")
    private String email;

    @SerializedName("address")
    private String address;

    @SerializedName("squad")
    private List<Player> squad; //list of team's players

    @SerializedName("activeCompetitions")
    private List<Competition> activeCompetitions; //list of team's competitions

    @SerializedName("crestUrl")
    private String crestUrl;

    @SerializedName("website")
    private String website;

    @SerializedName("clubColors")
    private String clubColors; //list of team's colours

    @SerializedName("venue")
    private String venue;

    @SerializedName("founded")
    private String founded;

    public Team(String id, Area area, String name, String tla, String email, String address, List<Player> squad, List<Competition> activeCompetitions, String clubColors, String crestUrl, String founded, String venue, String website) {
        this.id = id;
        this.area = area;
        this.name = name;
        this.tla = tla;
        this.email = email;
        this.address = address;
        this.squad = squad;
        this.activeCompetitions = activeCompetitions;
        this.clubColors = clubColors;
        this.crestUrl = crestUrl;
        this.website = website;
        this.venue = venue;
        this.founded = founded;
    }

    public Area getArea() { return area; }

    public String getCrestUrl() { return crestUrl; }

    public String getWebsite() { return website;}

    public String getName() { return name; }

    public String getId() { return id; }

    public String getTla() { return tla; }

    public String getEmail() { return email; }

    public String getAddress() { return address; }

    public List<Player> getSquad() { return squad; }

    public List<Competition> getActiveCompetitions() { return activeCompetitions; }

    public String getVenue() { return venue; }

    public String getClubColors() { return clubColors; }

    public String getFounded() { return founded; }
}
