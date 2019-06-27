package com.example.golo;

import android.graphics.Bitmap;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;
import com.example.Models.Competition.Competition;
import com.example.Models.Competition.CompetitionSeason;

import java.util.ArrayList;
import java.util.List;

public class CompetitionActivity extends AppCompatActivity {
    private final String url = "http://api.football-data.org/v2/competitions/";
    private Competition competition;
    private DataSource<Competition> data = new DataSource<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Bundle extras = getIntent().getExtras();
        String compId = extras.getString("compId");

        try {
            competition = data.getObjectfromJson(url+compId, Competition.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setTitle(competition.getName() + " - " + competition.getCode() + " | " + competition.getArea().getName());

        ImageView imageView = findViewById(R.id.competitionIcon);
        if (competition.getName().equals("Primeira Liga"))
                imageView.setImageResource(R.drawable.ic_liganos);
        else if(competition.getName().equals("Ligue 1"))
                imageView.setImageResource(R.drawable.ic_ligueone);
        else if (competition.getName().equals("Serie A"))
                imageView.setImageResource(R.drawable.ic_seriea);
        else if (competition.getName().equals("Primera Division"))
                imageView.setImageResource(R.drawable.ic_laliga);
        else if (competition.getName().equals("Championship"))
                imageView.setImageResource(R.drawable.ic_championship);
        else if (competition.getName().equals("Premier League"))
                imageView.setImageResource(R.drawable.ic_premierleague);
        else if (competition.getName().equals("UEFA Champions League"))
                imageView.setImageResource(R.drawable.ic_champions);
        else if (competition.getName().equals("European Championship"))
                imageView.setImageResource(R.drawable.ic_euro2016);
        else if (competition.getName().equals("Bundesliga"))
                imageView.setImageResource(R.drawable.ic_bundesliga);
        else if (competition.getName().equals("Eredivisie"))
                imageView.setImageResource(R.drawable.ic_eredivisie);
        else if (competition.getName().equals("FIFA World Cup"))
                imageView.setImageResource(R.drawable.ic_worldcup);
    }
}
