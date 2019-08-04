package com.example.golo;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
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
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;

public class CompetitionActivity extends AppCompatActivity implements RecyclerViewStandingAdapter.ItemClickListener, RecyclerViewScorersAdapter.ItemClickListener,RecyclerViewTeamAdapter.ItemClickListener{
    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private RecyclerViewTeamAdapter recyclerViewTeamAdapter;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private String compId;
    private GetDataService apiService;
    private ProgressBar progressBar;
    private Competition comp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true); //para as imagens .xml

        Bundle extras = getIntent().getExtras();
        compId = extras.getString("compId"); //vou buscar o ID da competição seleccionada anteriormente

        progressBar = findViewById(R.id.progressBarId);

        apiService = RetrofitClient.getRetrofitInstance().create(GetDataService.class);
        apiService.getCompetition(compId).observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Competition>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Competition competition) {
                comp = competition;
                if (Integer.parseInt(competition.getId()) == 2002 ||
                        Integer.parseInt(competition.getId()) == 2003 ||
                        Integer.parseInt(competition.getId()) == 2013 ||
                        Integer.parseInt(competition.getId()) == 2014 ||
                        Integer.parseInt(competition.getId()) == 2015 ||
                        Integer.parseInt(competition.getId()) == 2016 ||
                        Integer.parseInt(competition.getId()) == 2019 ||
                        Integer.parseInt(competition.getId()) == 2021) {

                    setToolbarInfo();
                    tabLayout = findViewById(R.id.tabLayoutId);
                    viewPager = findViewById(R.id.viewPagerId);
                    viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

                    apiService = RetrofitClient.getRetrofitInstance().create(GetDataService.class);
                    apiService.getStandings(compId).observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Standing>(){
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Standing standing) {
                            progressBar.setVisibility(View.GONE);
                            ArrayList<StandingType> standingTeams = new ArrayList<>(standing.getStandings().size());
                            standingTeams.addAll(standing.getStandings());
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

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                }else { //caso não existam standings na API, mostra as equipas que disputam a competição
                    setContentView(R.layout.teams_competition);
                    setToolbarInfo();

                    progressBar.setVisibility(View.VISIBLE);

                    apiService = RetrofitClient.getRetrofitInstance().create(GetDataService.class);
                    apiService.getTeams(compId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<TeamList>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(TeamList teamList) {
                            recyclerView = findViewById(R.id.teamsRecyclerView);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerViewTeamAdapter = new RecyclerViewTeamAdapter(getApplicationContext(), teamList.getTeams(), compId);
                            recyclerViewTeamAdapter.setClickListener(CompetitionActivity.this::onItemClick);
                            recyclerView.setAdapter(recyclerViewTeamAdapter);
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void setToolbarInfo(){
        toolbar = findViewById(R.id.toolbar);
        setIconToolbar();
        setSupportActionBar(toolbar);
        String[] startYear = comp.getCurrentSeason().getStartDate().split(("-"));
        String[] endYear = comp.getCurrentSeason().getEndDate().split(("-"));
        String currentSeason = startYear[0] + "/" + endYear[0];
        getSupportActionBar().setTitle("\t" + comp.getName() + " - " + currentSeason);
    }

    public void setIconToolbar(){
        switch(comp.getArea().getName()){
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
