package com.example.golo;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
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

import java.util.ArrayList;

public class CompetitionActivity extends AppCompatActivity implements RecyclerViewStandingAdapter.ItemClickListener, RecyclerViewScorersAdapter.ItemClickListener,RecyclerViewTeamAdapter.ItemClickListener{
    private Competition competition;
    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private RecyclerViewTeamAdapter recyclerViewTeamAdapter;
    private RecyclerView recyclerView;
    private TeamList teamList;
    private Toolbar toolbar;
    private String[] startYear, endYear;
    private String currentSeason;
    private Standing standing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true); //para as imagens .xml


        Bundle extras = getIntent().getExtras();
        String compId = extras.getString("compId"); //vou buscar o ID da competição seleccionada anteriormente

        try {  //verificar se o objecto seleccionado já existe para não fazer request sempre do mesmo objecto
            DataSource<Competition> data = new DataSource<>();
            competition = data.getObjectfromJson(data.getUrl() + compId, Competition.class);
        } catch (Exception e) {
            if(e.getMessage().equals("429"))
                Toast.makeText(getApplicationContext(),"Too many requests!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        setToolbarInfo();

        //caso a API retorne standings
        if (Integer.parseInt(competition.getId()) == 2013 || Integer.parseInt(competition.getId())== 2019 ||
                Integer.parseInt(competition.getId()) == 2021) {
            tabLayout = findViewById(R.id.tabLayoutId);
            viewPager = findViewById(R.id.viewPagerId);
            viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

            DataSource<Standing> standingDataSource = new DataSource<>();
            try {
                standing = standingDataSource.getObjectfromJson(standingDataSource.getUrl()+compId+"/standings", Standing.class);
            } catch (Exception e) {
                if(e.getMessage().equals("429"))
                    Toast.makeText(getApplicationContext(),"Too many requests!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            ArrayList<StandingType> standingTeams = new ArrayList<>(standing.getStandings().size());
            standingTeams.addAll(standing.getStandings());
            extras.putSerializable("standings", standingTeams);

            FragmentTotal fragmentTotal = new FragmentTotal();
            FragmentHome fragmentHome = new FragmentHome();
            FragmentAway fragmentAway = new FragmentAway();
            FragmentScorers fragmentScorers = new FragmentScorers();

            fragmentTotal.setArguments(extras); //envio os standings para os fragments (já não faço request nos fragments!)
            fragmentHome.setArguments(extras);
            fragmentAway.setArguments(extras);
            fragmentScorers.setArguments(extras);

            viewPagerAdapter.AddFragment(fragmentTotal, "TOTAL");
            viewPagerAdapter.AddFragment(fragmentHome, "HOME");
            viewPagerAdapter.AddFragment(fragmentAway, "AWAY");
            viewPagerAdapter.AddFragment(fragmentScorers, "SCORERS");
            viewPager.setAdapter(viewPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);

        } else { //caso não existam standings na API, mostra as equipas que disputam a competição
            setContentView(R.layout.teams_competition);
            setToolbarInfo();
            recyclerView = findViewById(R.id.teamsRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            DataSource<TeamList> dataSource = new DataSource<>();

            try {
                teamList = dataSource.getObjectfromJson(dataSource.getUrl() + compId + "/teams", TeamList.class);
            } catch (Exception e) {
                if(e.getMessage().equals("429"))
                    Toast.makeText(getApplicationContext(),"Too many requests!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            recyclerViewTeamAdapter = new RecyclerViewTeamAdapter(this, teamList.getTeams());
            recyclerViewTeamAdapter.setClickListener(this);
            recyclerView.setAdapter(recyclerViewTeamAdapter);
        }
    }

    public void setToolbarInfo(){
        toolbar = findViewById(R.id.toolbar);
        setIconToolbar();
        setSupportActionBar(toolbar);
        startYear = competition.getCurrentSeason().getStartDate().split(("-"));
        endYear = competition.getCurrentSeason().getEndDate().split(("-"));
        currentSeason = startYear[0] + "/" + endYear[0];
        getSupportActionBar().setTitle("\t" + competition.getName() + " - " + currentSeason);
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
            default: break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
