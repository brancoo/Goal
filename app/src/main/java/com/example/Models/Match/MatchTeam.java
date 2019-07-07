package com.example.Models.Match;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MatchTeam implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
