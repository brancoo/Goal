package com.example.Models.Team;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Winner implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("shortName")
    private String shortName;
    @SerializedName("alias")
    private String alias;

    public Winner(String id, String name, String shortName, String alias) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.alias = alias;
    }

    public String getId() {
        return id;
    }

    public String getShortName() {
        return shortName;
    }

    public String getName() { return name; }

    public String getAlias() {
        return alias;
    }

    @Override
    public String toString(){
        return "Nome: " + getName() + "\n" + "Nome encurtado: " + getShortName() + "\n" + "Alias: " + getAlias() + "\n";
    }
}
