package com.example.Models.Match;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SingleMatch implements Serializable {
    @SerializedName("head2head")
    private Head2Head head2head;

    @SerializedName("match")
    private Match match;

    public SingleMatch(Head2Head head2head, Match match) {
        this.head2head = head2head;
        this.match = match;
    }

    public Head2Head getHead2Head() {
        return head2head;
    }

    public Match getMatch() {
        return match;
    }
}
