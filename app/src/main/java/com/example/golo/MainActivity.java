package com.example.golo;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    private Map<String, String> mapOfCompetitions = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("GOLO");
        setSupportActionBar(toolbar);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        setData();
        List<String> compNames = new ArrayList<String>(mapOfCompetitions.keySet());

        RecyclerView recyclerView = findViewById(R.id.idCompetitionsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, compNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    private void setData(){
        DataSource<CompetitionList> data = new DataSource<>();
        try {
            CompetitionList compList = data.getObjectfromJson(data.getUrl(), CompetitionList.class);
            for (Competition competition : compList.getAvailableCompetitions())
                mapOfCompetitions.put(competition.getName(), competition.getId());
        } catch (Exception e) {
            if(e.getMessage().equals("429"))
                Toast.makeText(getApplicationContext(),"Too many requests!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick (View view, int position){
        String compName = adapter.getItem(position);
        String compId = mapOfCompetitions.get(compName);
        Intent intent = new Intent(MainActivity.this, CompetitionActivity.class);
        intent.putExtra("compId", compId); //sending compId to the new Activity
        startActivity(intent);
    }
}
