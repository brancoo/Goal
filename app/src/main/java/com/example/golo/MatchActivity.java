package com.example.golo;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.Models.Match.SingleMatch;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

import java.util.ArrayList;
import java.util.List;

public class MatchActivity extends AppCompatActivity {
    private DataSource<SingleMatch> dataSource = new DataSource<>();
    private SingleMatch singleMatch;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        Bundle extras = getIntent().getExtras();
        String matchId = extras.getString("matchId");

        try {
            singleMatch = dataSource.getObjectfromJson(dataSource.getUrl() + "matches/" + matchId, SingleMatch.class);
        } catch (Exception e) {
            if(e.getMessage().equals("429"))
                Toast.makeText(getApplicationContext(),"Too many requests!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Matchday: " + singleMatch.getMatch().getMatchday() + " | " +  singleMatch.getMatch().getUtcDate().substring(0,10));

        TextView textView = findViewById(R.id.h2hTotalGamesId);
        textView.setText(singleMatch.getHead2Head().getNumberOfMatches() + " GAMES IN TOTAL");
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        TextView matchTeams = findViewById(R.id.matchTeamsId);
        TextView matchStadium = findViewById(R.id.matchStadiumId);
        TextView matchStage = findViewById(R.id.matchStageId);
        TextView matchDate = findViewById(R.id.matchDateId);
        TextView matchFinalScore = findViewById(R.id.matchFinalScoreId);
        TextView matchHalfTimeScore = findViewById(R.id.matchHalfTimeScoreId);

        matchTeams.setText(singleMatch.getMatch().getHomeTeam().getName() + " vs. " + singleMatch.getMatch().getAwayTeam().getName());
        matchStadium.setText("Stadium: " + singleMatch.getMatch().getVenue());
        matchStage.setText("Stage: " + singleMatch.getMatch().getStage().replace("_"," "));
        matchDate.setText("Match Date: " + singleMatch.getMatch().getUtcDate().substring(0,10));

        if(singleMatch.getMatch().getStatus().equals("FINISHED")) {
            matchFinalScore.setText("Final Score: " + singleMatch.getMatch().getHomeTeam().getName() + " " + singleMatch.getMatch().getScore().getFullTime().getHomeTeam() + " - " + singleMatch.getMatch().getAwayTeam().getName() +" " + singleMatch.getMatch().getScore().getFullTime().getAwayTeam());
            matchHalfTimeScore.setText("HalfTime Score: " + singleMatch.getMatch().getHomeTeam().getName() + " " + singleMatch.getMatch().getScore().getHalfTime().getHomeTeam() + " - " + singleMatch.getMatch().getAwayTeam().getName() + " " + singleMatch.getMatch().getScore().getHalfTime().getAwayTeam());
        }else{
            matchFinalScore.setVisibility(View.INVISIBLE);
            matchHalfTimeScore.setVisibility(View.INVISIBLE);
        }

        final PieChartView pieChartView = findViewById(R.id.pieChartId);
        List<SliceValue> pieData = new ArrayList<>();
        int wins = singleMatch.getHead2Head().getHomeTeam().getWins();
        int draws = singleMatch.getHead2Head().getHomeTeam().getDraws();
        int losses = singleMatch.getHead2Head().getHomeTeam().getLosses();
        pieData.add(new SliceValue(singleMatch.getHead2Head().getHomeTeam().getWins(), Color.GREEN).setLabel(wins + " HOME WINS"));
        pieData.add(new SliceValue(singleMatch.getHead2Head().getHomeTeam().getDraws(), Color.GRAY).setLabel(draws + " HOME DRAWS"));
        pieData.add(new SliceValue(singleMatch.getHead2Head().getHomeTeam().getLosses(), Color.RED).setLabel(losses + " HOME LOSSES"));

        final PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(11);
        pieChartData.getSlicesSpacing();
        pieChartView.setPieChartData(pieChartData);

    }
}
