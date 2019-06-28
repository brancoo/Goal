package com.example.golo;

import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import com.example.Models.Competition.Competition;

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

        TextView textView = findViewById(R.id.compInfo);
        textView.setText("Código da Competição: "+competition.getCode()+"\n"+"Nome do País da Competição: "+
                        competition.getArea().getName()+"\n"+"ID da Competição: "+competition.getId());

    }
}
