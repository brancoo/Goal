package com.example.golo;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.example.Models.Match.Match;
import com.example.Models.Match.MatchList;
import com.example.Models.Player.Player;
import com.example.Models.Team.Team;
import com.example.golo.Fragments.FragmentTeamInfo;
import com.example.golo.Fragments.FragmentTeamMatches;
import com.example.golo.Fragments.FragmentTeamSquad;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class TeamActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private TeamViewPagerAdapter teamViewPagerAdapter;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private Team team;
    private MatchList matchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        Bundle extras = getIntent().getExtras();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("\t" + extras.getString("teamName"));

        DataSource<Team> dataSource = new DataSource<>();
        try {
            team = dataSource.getObjectfromJson(dataSource.getUrl() + "teams/" + extras.getString("teamId"),Team.class);
        } catch (Exception e) {
            if(e.getMessage().equals("429"))
                Toast.makeText(getApplicationContext(),"Too many requests!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        DataSource<MatchList> dataMatches = new DataSource<>();
        try {
            matchList = dataMatches.getObjectfromJson(dataMatches.getUrl() +"competitions/" + extras.getString("compId") + "/matches",MatchList.class);
        } catch (Exception e) {
            if(e.getMessage().equals("429"))
                Toast.makeText(getApplicationContext(),"Too many requests!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        tabLayout = findViewById(R.id.tabLayoutTeamId);
        viewPager = findViewById(R.id.teamViewPagerId);
        teamViewPagerAdapter = new TeamViewPagerAdapter(getSupportFragmentManager());

        FragmentTeamInfo fragmentTeamInfo = new FragmentTeamInfo();
        extras.putSerializable("team",team);
        fragmentTeamInfo.setArguments(extras);

        FragmentTeamSquad fragmentTeamSquad = new FragmentTeamSquad();
        ArrayList<Player> teamSquad = new ArrayList<>(team.getSquad().size());
        teamSquad.addAll(team.getSquad());
        extras.putSerializable("teamSquad", teamSquad);
        fragmentTeamSquad.setArguments(extras);

        FragmentTeamMatches fragmentTeamMatches = new FragmentTeamMatches();
        ArrayList<Match> matches = new ArrayList<>();
        for(int i = 0; i < matchList.getMatches().size(); i++){
            if(matchList.getMatches().get(i).getAwayTeam().getName().equals(team.getName()) || matchList.getMatches().get(i).getHomeTeam().getName().equals(team.getName()))
                matches.add(matchList.getMatches().get(i));
        }
        extras.putSerializable("teamMatches", matches);
        fragmentTeamMatches.setArguments(extras);

        teamViewPagerAdapter.AddFragment(fragmentTeamInfo, getString(R.string.team_info));
        teamViewPagerAdapter.AddFragment(fragmentTeamSquad, getString(R.string.team_squad));
        teamViewPagerAdapter.AddFragment(fragmentTeamMatches,getString(R.string.team_matches));
        viewPager.setAdapter(teamViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.info_icon);
        tabLayout.getTabAt(0).getIcon().setTint(Color.WHITE);
        tabLayout.getTabAt(1).setIcon(R.drawable.squad_icon);
        tabLayout.getTabAt(1).getIcon().setTint(Color.WHITE);
        tabLayout.getTabAt(2).setIcon(R.drawable.matches_icon);
        tabLayout.getTabAt(2).getIcon().setTint(Color.WHITE);
    }
}
