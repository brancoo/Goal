package com.example.golo;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.example.Models.Competition.Competition;
import com.example.Models.Standing.Standing;
import com.example.Models.Standing.StandingType;
import com.example.Models.Team.TeamList;
import com.example.golo.Fragments.FragmentAway;
import com.example.golo.Fragments.FragmentHome;
import com.example.golo.Fragments.FragmentScorers;
import com.example.golo.Fragments.FragmentTotal;
import com.google.android.material.tabs.TabLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class CompetitionActivity extends AppCompatActivity implements RecyclerViewStandingAdapter.ItemClickListener, RecyclerViewScorersAdapter.ItemClickListener,RecyclerViewTeamAdapter.ItemClickListener{
    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private RecyclerViewTeamAdapter recyclerViewTeamAdapter;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private String[] startYear, endYear;
    private String currentSeason;
    private String compId;
    private GetDataService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true); //para as imagens .xml

        Bundle extras = getIntent().getExtras();
        compId = extras.getString("compId"); //vou buscar o ID da competição seleccionada anteriormente

        apiService = RetrofitClient.getRetrofitInstance().create(GetDataService.class);
        Call<Competition> competitionCall = apiService.getCompetition(compId);
        competitionCall.enqueue(new Callback<Competition>() {
            @Override
            public void onResponse(Call<Competition> call, Response<Competition> response) {
                if (response.isSuccessful()) {
                    //caso retorne standings
                    if (Integer.parseInt(response.body().getId()) == 2013 ||
                            Integer.parseInt(response.body().getId()) == 2019 ||
                            Integer.parseInt(response.body().getId()) == 2021 ||
                            Integer.parseInt(response.body().getId()) == 2002 ||
                            Integer.parseInt(response.body().getId()) == 2003) {

                        setToolbarInfo(response);
                        tabLayout = findViewById(R.id.tabLayoutId);
                        viewPager = findViewById(R.id.viewPagerId);
                        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

                        apiService = RetrofitClient.getRetrofitInstance().create(GetDataService.class);
                        Call<Standing> standingCall = apiService.getStandings(compId);
                        standingCall.enqueue(new Callback<Standing>() {
                            @Override
                            public void onResponse(Call<Standing> call, Response<Standing> response) {
                                if (response.isSuccessful()) {
                                    ArrayList<StandingType> standingTeams = new ArrayList<>(response.body().getStandings().size());
                                    standingTeams.addAll(response.body().getStandings());
                                    extras.putSerializable("standings", standingTeams);
                                    extras.putString("compId", compId);

                                    FragmentTotal fragmentTotal = new FragmentTotal();
                                    FragmentHome fragmentHome = new FragmentHome();
                                    FragmentAway fragmentAway = new FragmentAway();
                                    FragmentScorers fragmentScorers = new FragmentScorers();

                                    fragmentTotal.setArguments(extras); //envio os standings para os fragments
                                    fragmentHome.setArguments(extras);
                                    fragmentAway.setArguments(extras);
                                    fragmentScorers.setArguments(extras);

                                    viewPagerAdapter.AddFragment(fragmentTotal, "TOTAL");
                                    viewPagerAdapter.AddFragment(fragmentHome, "HOME");
                                    viewPagerAdapter.AddFragment(fragmentAway, "AWAY");
                                    viewPagerAdapter.AddFragment(fragmentScorers, "SCORERS");
                                    viewPager.setAdapter(viewPagerAdapter);
                                    tabLayout.setupWithViewPager(viewPager);
                                }
                            }

                            @Override
                            public void onFailure(Call<Standing> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }else { //caso não existam standings na API, mostra as equipas que disputam a competição
                        setContentView(R.layout.teams_competition);
                        setToolbarInfo(response);
                        apiService = RetrofitClient.getRetrofitInstance().create(GetDataService.class);
                        Call<TeamList> teamListCall = apiService.getTeams(compId);
                        teamListCall.enqueue(new Callback<TeamList>() {
                            @Override
                            public void onResponse(Call<TeamList> call, Response<TeamList> response) {
                                if(response.isSuccessful()){
                                    recyclerView = findViewById(R.id.teamsRecyclerView);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    recyclerViewTeamAdapter = new RecyclerViewTeamAdapter(getApplicationContext(), response.body().getTeams(), compId);
                                    recyclerViewTeamAdapter.setClickListener(CompetitionActivity.this::onItemClick);
                                    recyclerView.setAdapter(recyclerViewTeamAdapter);
                                }
                            }
                            @Override
                            public void onFailure(Call<TeamList> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<Competition> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void setToolbarInfo(Response<Competition> response){
        toolbar = findViewById(R.id.toolbar);
        setIconToolbar(response);
        setSupportActionBar(toolbar);
        startYear = response.body().getCurrentSeason().getStartDate().split(("-"));
        endYear = response.body().getCurrentSeason().getEndDate().split(("-"));
        currentSeason = startYear[0] + "/" + endYear[0];
        getSupportActionBar().setTitle("\t" + response.body().getName() + " - " + currentSeason);
    }

    public void setIconToolbar(Response<Competition> response){
        switch(response.body().getArea().getName()){
            case "Portugal":toolbar.setLogo(R.drawable.ic_portugal); break;
            case "Spain":   toolbar.setLogo(R.drawable.ic_spain); break;
            case "Germany": toolbar.setLogo(R.drawable.ic_germany); break;
            case "France":  toolbar.setLogo(R.drawable.ic_france); break;
            case "Netherlands": toolbar.setLogo(R.drawable.ic_netherlands); break;
            case "Italy": toolbar.setLogo(R.drawable.ic_italy); break;
            case "England":   toolbar.setLogo(R.drawable.ic_england); break;
            case "Brazil":    toolbar.setLogo(R.drawable.ic_brazil); break;
            case "World":
            case "Europe":  { toolbar.setLogo(R.drawable.ic_europe); break; }
            default: break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
