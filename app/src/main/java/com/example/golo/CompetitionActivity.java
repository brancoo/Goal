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
