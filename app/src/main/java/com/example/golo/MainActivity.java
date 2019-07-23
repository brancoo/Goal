package com.example.golo;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.Models.Competition.Competition;
import com.example.Models.Competition.CompetitionList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {
    private RecyclerViewAdapter adapter;
    private Map<String, String> mapOfCompetitions = new HashMap<String, String>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<String> compNames = new ArrayList<>();
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

        if(!ConnectivityHelper.isConnectedToNetwork(getApplicationContext())){
            new AlertDialog.Builder(this)
                    .setTitle("Something gone wrong!")
                    .setMessage("\nCheck your internet connection")
                    .setPositiveButton("Ok",null)
                    .show();
        }else {
            setData();

            swipeRefreshLayout = findViewById(R.id.swipeRefreshLayoutId);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    setData();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    private void setData(){
        DataSource<CompetitionList> data = new DataSource<>();
        try {
            CompetitionList compList = data.getObjectfromJson(data.getUrl()+"competitions", CompetitionList.class);
            for (Competition competition : compList.getAvailableCompetitions())
                mapOfCompetitions.put(competition.getName(), competition.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        compNames = new ArrayList<>(mapOfCompetitions.keySet());
        RecyclerView recyclerView = findViewById(R.id.idCompetitionsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, compNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
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
