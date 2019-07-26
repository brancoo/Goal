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
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
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
        Call<CompetitionList> compList = apiService.getCompetitions();
        compList.enqueue(new Callback<CompetitionList>() {
            @Override
            public void onResponse(Call<CompetitionList> call, Response<CompetitionList> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()) {
                    for(Competition competition : response.body().getAvailableCompetitions())
                        mapOfCompetitions.put(competition.getName(), competition.getId());
                    List<String> values = new ArrayList<>(mapOfCompetitions.keySet());
                    generateDataList(values);
                }else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(MainActivity.this, "HAVE TO WAIT: " + jObjError.getString("message").substring(37,39)+ " seconds!", Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CompetitionList> call, Throwable t) {
                t.printStackTrace();
                progressBar.setVisibility(View.GONE);
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
