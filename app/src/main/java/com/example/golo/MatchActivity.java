package com.example.golo;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.Models.Match.SingleMatch;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class MatchActivity extends AppCompatActivity {
    private GetDataService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        Bundle extras = getIntent().getExtras();

        TextView matchTeams = findViewById(R.id.matchTeamsId);
        TextView matchStadium = findViewById(R.id.matchStadiumId);
        TextView matchStage = findViewById(R.id.matchStageId);
        TextView matchDate = findViewById(R.id.matchDateId);
        TextView matchFinalScore = findViewById(R.id.matchFinalScoreId);
        TextView matchHalfTimeScore = findViewById(R.id.matchHalfTimeScoreId);

        apiService = RetrofitClient.getRetrofitInstance().create(GetDataService.class);
        Call<SingleMatch> singleMatchCall = apiService.getSingleMatch(extras.getString("matchId"));
        singleMatchCall.enqueue(new Callback<SingleMatch>() {
            @Override
            public void onResponse(Call<SingleMatch> call, Response<SingleMatch> response) {
                Toolbar toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle("Matchday: " + response.body().getMatch().getMatchday() + " | " +  response.body().getMatch().getUtcDate().substring(0,10));

                matchTeams.setText(response.body().getMatch().getHomeTeam().getName() + " vs. " + response.body().getMatch().getAwayTeam().getName());
                matchStadium.setText("Stadium: " + response.body().getMatch().getVenue());
                matchStage.setText("Stage: " + response.body().getMatch().getStage().replace("_", " "));
                matchDate.setText("Match Date: " + response.body().getMatch().getUtcDate().substring(0, 10));

                if (response.body().getMatch().getStatus().equals("FINISHED")) {
                    matchFinalScore.setText("Final Score: " + response.body().getMatch().getHomeTeam().getName() + " " + response.body().getMatch().getScore().getFullTime().getHomeTeam() + " - " + response.body().getMatch().getAwayTeam().getName() + " " + response.body().getMatch().getScore().getFullTime().getAwayTeam());
                    matchHalfTimeScore.setText("HalfTime Score: " + response.body().getMatch().getHomeTeam().getName() + " " + response.body().getMatch().getScore().getHalfTime().getHomeTeam() + " - " + response.body().getMatch().getAwayTeam().getName() + " " + response.body().getMatch().getScore().getHalfTime().getAwayTeam());
                } else {
                    matchFinalScore.setVisibility(View.INVISIBLE);
                    matchHalfTimeScore.setVisibility(View.INVISIBLE);
                }

                if(response.body().getHead2Head() != null) {
                    TextView textView = findViewById(R.id.h2hTotalGamesId);
                    textView.setText(response.body().getHead2Head().getNumberOfMatches() + " GAMES IN TOTAL");
                    textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                    final PieChartView pieChartView = findViewById(R.id.pieChartId);
                    List<SliceValue> pieData = new ArrayList<>();
                    int wins = response.body().getHead2Head().getHomeTeam().getWins();
                    int draws = response.body().getHead2Head().getHomeTeam().getDraws();
                    int losses = response.body().getHead2Head().getHomeTeam().getLosses();
                    pieData.add(new SliceValue(response.body().getHead2Head().getHomeTeam().getWins(), Color.GREEN).setLabel(wins + " HOME WINS"));
                    pieData.add(new SliceValue(response.body().getHead2Head().getHomeTeam().getDraws(), Color.GRAY).setLabel(draws + " HOME DRAWS"));
                    pieData.add(new SliceValue(response.body().getHead2Head().getHomeTeam().getLosses(), Color.RED).setLabel(losses + " HOME LOSSES"));

                    final PieChartData pieChartData = new PieChartData(pieData);
                    pieChartData.setHasLabels(true).setValueLabelTextSize(11);
                    pieChartData.getSlicesSpacing();
                    pieChartView.setPieChartData(pieChartData);
                }else{
                    TextView textView = findViewById(R.id.h2hTotalGamesId);
                    textView.setText("H2H not available");

                }
            }

            @Override
            public void onFailure(Call<SingleMatch> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
