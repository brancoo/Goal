package com.example.golo;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import com.example.Models.Competition.Competition;

public class CompetitionActivity extends AppCompatActivity {
    private final String url = "http://api.football-data.org/v2/competitions/";
    private Competition competition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Bundle extras = getIntent().getExtras();
        String compId = extras.getString("compId");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            DataSource<Competition> data = new DataSource<>();
            competition = data.getObjectfromJson(url+compId, Competition.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getSupportActionBar().setTitle("\t"+competition.getName());
        toolbar.setLogo(R.drawable.ic_portugal);

    }
}
