package com.example.Models.Competition;

import com.example.Models.Area.Area;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Competition implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("area")
    private Area area;

    @SerializedName("name")
    private String name;

    @SerializedName("code")
    private String code;

    @SerializedName("plan")
    private String plan;

    @SerializedName("currentSeason")
    private CompetitionSeason currentSeason;

    @SerializedName("seasons")
    private List<CompetitionSeason> seasons;

    public Competition(String id, Area area, String name, String code, String plan, CompetitionSeason currentSeason, List<CompetitionSeason> seasons) {
        this.id = id;
        this.area = area;
        this.name = name;
        this.code = code;
        this.plan = plan;
        this.currentSeason = currentSeason;
        this.seasons = seasons;
    }

    public String getCode() {
        return code;
    }

    public CompetitionSeason getCurrentSeason() {
        return currentSeason;
    }

    public List<CompetitionSeason> getSeasons() {
        return seasons;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Area getArea() {
        return area;
    }

    @Override
    public String toString() {
        return "Competition{" +
                "id='" + id + '\'' +
                ", area=" + area +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", currentSeason=" + currentSeason +
                ", seasons=" + seasons +
                '}';
    }

}
