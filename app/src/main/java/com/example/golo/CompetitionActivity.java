package com.example.golo;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.example.Models.Competition.Competition;
import com.example.Models.Standing.Standing;
import com.example.Models.Team.TeamList;
import com.example.golo.Fragments.FragmentAway;
import com.example.golo.Fragments.FragmentHome;
import com.example.golo.Fragments.FragmentScorers;
import com.example.golo.Fragments.FragmentTotal;
import com.google.android.material.tabs.TabLayout;

public class CompetitionActivity extends AppCompatActivity implements RecyclerViewStandingAdapter.ItemClickListener {
    private String url = "http://api.football-data.org/v2/competitions/";
    private Competition competition;
    private Standing standing;
    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private RecyclerViewStandingAdapter adapter;
    private RecyclerViewTeamAdapter recyclerViewTeamAdapter;
    private RecyclerView recyclerView;
    private TeamList teamList;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true); //para as imagens .xml

        Bundle extras = getIntent().getExtras();
        String compId = extras.getString("compId"); //vou buscar o ID da competição seleccionada anteriormente

        try {  //verificar se o objecto seleccionado já existe para não fazer request sempre do mesmo objecto
                DataSource<Competition> data = new DataSource<>();
                competition = data.getObjectfromJson(url + compId, Competition.class);
        } catch (Exception e) {
                e.printStackTrace();
        }

        toolbar = findViewById(R.id.toolbar);
        setIconToolbar();
        setSupportActionBar(toolbar);

        String[] startYear = competition.getCurrentSeason().getStartDate().split(("-"));
        String[] endYear = competition.getCurrentSeason().getEndDate().split(("-"));
        String currentSeason = startYear[0] + "/" + endYear[0];
        getSupportActionBar().setTitle("\t"+competition.getName() + " - " + currentSeason);

        if(competition.getId() == "2013" || competition.getId() == "2014" || competition.getId() == "2019" ||
                competition.getId() == "2021") {
            tabLayout = findViewById(R.id.tabLayoutId);
            viewPager = findViewById(R.id.viewPagerId);
            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            Bundle bundle = new Bundle();
            bundle.putString("compId", compId); //para enviar o ID da competição seleccionada para cada fragmento
            FragmentTotal fragmentTotal = new FragmentTotal();
            FragmentHome fragmentHome = new FragmentHome();
            FragmentAway fragmentAway = new FragmentAway();
            FragmentScorers fragmentScorers = new FragmentScorers();
            fragmentTotal.setArguments(bundle); //envio para cada fragmento o ID
            fragmentHome.setArguments(bundle);
            fragmentAway.setArguments(bundle);
            fragmentScorers.setArguments(bundle);
            viewPagerAdapter.AddFragment(fragmentTotal, "TOTAL");
            viewPagerAdapter.AddFragment(fragmentHome, "HOME");
            viewPagerAdapter.AddFragment(fragmentAway, "AWAY");
            viewPagerAdapter.AddFragment(fragmentScorers, "SCORERS");
            viewPager.setAdapter(viewPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
        }
            setContentView(R.layout.teams_competition);
            toolbar = findViewById(R.id.toolbar);
            setIconToolbar();
            setSupportActionBar(toolbar);
            startYear = competition.getCurrentSeason().getStartDate().split(("-"));
            endYear = competition.getCurrentSeason().getEndDate().split(("-"));
            currentSeason = startYear[0] + "/" + endYear[0];
            getSupportActionBar().setTitle("\t"+competition.getName() + " - " + currentSeason);

            recyclerView = findViewById(R.id.teamsRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            DataSource<TeamList> dataSource = new DataSource<>();
            try {
                teamList = dataSource.getObjectfromJson(url+compId+"/teams", TeamList.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            recyclerViewTeamAdapter = new RecyclerViewTeamAdapter(this, teamList.getTeams());
            recyclerView.setAdapter(recyclerViewTeamAdapter);
    }

    public void setIconToolbar(){
        switch(competition.getArea().getName()){
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
        }
    }
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getApplicationContext(),"OLA", Toast.LENGTH_SHORT).show();
    }
}
