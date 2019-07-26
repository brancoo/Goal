package com.example.golo;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.Models.Competition.Competition;
import com.example.Models.Competition.CompetitionList;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {
    private RecyclerViewAdapter adapter;
    private final Map<String, String> mapOfCompetitions = new HashMap<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayoutId);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            setData();
            swipeRefreshLayout.setRefreshing(false);
        });

        toolbar.setTitle("GOLO");
        setSupportActionBar(toolbar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if(!ConnectivityHelper.isConnectedToNetwork(getApplicationContext())){
            new AlertDialog.Builder(this)
                    .setTitle("Something gone wrong!")
                    .setMessage("\nCheck your internet connection")
                    .setPositiveButton("Ok",null)
                    .show();
        }else {
            setData();
        }
    }

    private void setData() {
        // Set up progress before call
        final ProgressBar progressBar;
        progressBar = findViewById(R.id.progressBarId);

        GetDataService apiService = RetrofitClient.getRetrofitInstance().create(GetDataService.class);
        apiService.getCompetitions().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<CompetitionList>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CompetitionList competitionList) {
                progressBar.setVisibility(View.GONE);
                for(Competition competition : competitionList.getAvailableCompetitions())
                    mapOfCompetitions.put(competition.getName(), competition.getId());
                List<String> values = new ArrayList<>(mapOfCompetitions.keySet());
                generateDataList(values);
            }

            @Override
            public void onError(Throwable e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "DEU BOSTA", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "COMPLETED!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<String> competitionList) {
        RecyclerView recyclerView = findViewById(R.id.idCompetitionsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, competitionList);
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
