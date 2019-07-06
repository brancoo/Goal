package com.example.golo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.example.Models.Player.Player;
import com.example.Models.Standing.StandingType;
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
            team = dataSource.getObjectfromJson("http://api.football-data.org/v2/teams/"+extras.getString("teamId"),Team.class);
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
        teamViewPagerAdapter.AddFragment(fragmentTeamInfo, "TEAM INFO");
        teamViewPagerAdapter.AddFragment(fragmentTeamSquad, "SQUAD");
        teamViewPagerAdapter.AddFragment(fragmentTeamMatches,"MATCHES");
        viewPager.setAdapter(teamViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.info_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.squad_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.matches_icon);
    }
}
