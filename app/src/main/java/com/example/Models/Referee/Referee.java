package com.example.Models.Referee;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Referee implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("nationality")
    private String nationality;

    public Referee(String id, String name, String nationality) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    @Override
    public String toString() {
        return "Name='" + name + '\'' +  ", nationality='" + nationality + '\'';
    }
}
