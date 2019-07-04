package com.example.Models.Standing;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class StandingType implements Serializable{
    @SerializedName("stage")
    private String stage;

    @SerializedName("type")
    private String type;

    @SerializedName("group")
    private String group;

    @SerializedName("table")
    private List<StandingTeam> table;

    public StandingType(String stage, String type, String group, List<StandingTeam> table) {
        this.stage = stage;
        this.type = type;
        this.group = group;
        this.table = table;
    }

    public String getStage() { return stage;}

    public String getType() {
        return type;
    }

    public String getGroup() {
        return group;
    }

    public List<StandingTeam> getTable() { return table;}
}
