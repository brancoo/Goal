package com.example.golo;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.Models.Competition.Competition;
import com.example.Models.Competition.CompetitionList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {

    RecyclerViewAdapter adapter;
    private Map<String,String> mapOfCompetitions = new HashMap<String, String>();
    private final String url = "http://api.football-data.org/v2/competitions";
    private int compIcons[] = { R.drawable.ic_liganos, R.drawable.ic_champions, R.drawable.ic_ligueone, R.drawable.ic_worldcup,
                                R.drawable.ic_championship, R.drawable.ic_eredivisie, R.drawable.ic_bundesliga,
                                R.drawable.ic_premierleague, R.drawable.ic_euro2016, R.drawable.ic_seriea, R.drawable.ic_laliga,
                                R.drawable.ic_seriea};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        DataSource<CompetitionList> data = new DataSource<>();
        try {
            CompetitionList compList = data.getObjectfromJson(url, CompetitionList.class);
            for(Competition competition : compList.getAvailableCompetitions())
                mapOfCompetitions.put(competition.getName(), competition.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> compNames = new ArrayList<String>(mapOfCompetitions.keySet());

        RecyclerView recyclerView = findViewById(R.id.idComp);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, compNames, compIcons);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        String compName = adapter.getItem(position);
        String compId = mapOfCompetitions.get(compName);
        Intent intent = new Intent(MainActivity.this, CompetitionActivity.class);
        intent.putExtra("compId", compId); //sending compId to the new Activity
        startActivity(intent);
    }
}
