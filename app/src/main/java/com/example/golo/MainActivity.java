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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    private void setData() {
        GetDataService apiService = RetrofitClient.getRetrofitInstance().create(GetDataService.class);
        Call<CompetitionList> compList = apiService.getCompetitions();
        compList.enqueue(new Callback<CompetitionList>() {
            @Override
            public void onResponse(Call<CompetitionList> call, Response<CompetitionList> response) {
                if(response.isSuccessful())
                    generateDataList(response.body().getAvailableCompetitions());
            }
            @Override
            public void onFailure(Call<CompetitionList> call, Throwable t) {
                Log.d("ERROR", "ERROR: " + t.getMessage());
            }
        });
/*
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
        recyclerView.setAdapter(adapter); */
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<Competition> competitionList) {
        RecyclerView recyclerView = findViewById(R.id.idCompetitionsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, competitionList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick (View view, int position){
        String compName = adapter.getItem(position).getName();
        String compId = mapOfCompetitions.get(compName);
        Intent intent = new Intent(MainActivity.this, CompetitionActivity.class);
        intent.putExtra("compId", compId); //sending compId to the new Activity
        startActivity(intent);
    }
}
