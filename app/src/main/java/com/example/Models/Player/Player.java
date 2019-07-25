package com.example.Models.Player;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Player implements Parcelable {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("position")
    private String position;

    @SerializedName("dateOfBirth")
    private String dateOfBirth;

    @SerializedName("shirtNumber")
    private String shirtNumber;

    @SerializedName("role")
    private String role; //coach | assistant coach | player

    @SerializedName("countryOfBirth")
    private String countryOfBirth;

    @SerializedName("nationality")
    private String nationality;

    public Player(String id, String name, String position, String dateOfBirth, String countryOfBirth, String nationality, String shirtNumber, String role) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.dateOfBirth = dateOfBirth;
        this.countryOfBirth = countryOfBirth;
        this.nationality = nationality;
        this.shirtNumber = shirtNumber;
        this.role = role;
    }

    public String getShirtNumber() {
        return shirtNumber;
    }

    public String getRole() {
        return role;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
