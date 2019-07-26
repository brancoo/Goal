package com.example.golo;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.Models.Match.SingleMatch;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatchActivity extends AppCompatActivity {
    private SingleMatch singleMatch;

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

        GetDataService apiService = RetrofitClient.getRetrofitInstance().create(GetDataService.class);
        apiService.getSingleMatch(extras.getString("matchId")).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<SingleMatch>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(SingleMatch singleMatch) {
                Toolbar toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle("Matchday: " + singleMatch.getMatch().getMatchday() + " | " + singleMatch.getMatch().getUtcDate().substring(0, 10));

                matchTeams.setText(singleMatch.getMatch().getHomeTeam().getName() + " vs. " + singleMatch.getMatch().getAwayTeam().getName());
                matchStadium.setText("Stadium: " + singleMatch.getMatch().getVenue());
                matchStage.setText("Stage: " + singleMatch.getMatch().getStage().replace("_", " "));
                matchDate.setText("Match Date: " + singleMatch.getMatch().getUtcDate().substring(0, 10));

                if (singleMatch.getMatch().getStatus().equals("FINISHED")) {
                    matchFinalScore.setText("Final Score: " + singleMatch.getMatch().getHomeTeam().getName() + " " + singleMatch.getMatch().getScore().getFullTime().getHomeTeam() + " - " + singleMatch.getMatch().getAwayTeam().getName() + " " + singleMatch.getMatch().getScore().getFullTime().getAwayTeam());
                    matchHalfTimeScore.setText("HalfTime Score: " + singleMatch.getMatch().getHomeTeam().getName() + " " + singleMatch.getMatch().getScore().getHalfTime().getHomeTeam() + " - " + singleMatch.getMatch().getAwayTeam().getName() + " " + singleMatch.getMatch().getScore().getHalfTime().getAwayTeam());
                } else {
                    matchFinalScore.setVisibility(View.INVISIBLE);
                    matchHalfTimeScore.setVisibility(View.INVISIBLE);
                }

                if (singleMatch.getHead2Head() != null) {
                    TextView textView = findViewById(R.id.h2hTotalGamesId);
                    textView.setText(singleMatch.getHead2Head().getNumberOfMatches() + " GAMES IN TOTAL");
                    textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

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
                } else {
                    TextView textView = findViewById(R.id.h2hTotalGamesId);
                    textView.setText("H2H not available");
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MatchActivity.this, "DEU BOSTA", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                Toast.makeText(MatchActivity.this, "COMPLETED!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
