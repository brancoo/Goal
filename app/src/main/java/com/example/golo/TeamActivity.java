package com.example.golo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.example.golo.Fragments.FragmentTeamInfo;
import com.example.golo.Fragments.FragmentTeamMatches;
import com.example.golo.Fragments.FragmentTeamSquad;
import com.google.android.material.tabs.TabLayout;

public class TeamActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private TeamViewPagerAdapter teamViewPagerAdapter;
    private ViewPager viewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        Bundle extras = getIntent().getExtras();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("\t" + extras.getString("teamName"));

        tabLayout = findViewById(R.id.tabLayoutTeamId);
        viewPager = findViewById(R.id.teamViewPagerId);
        teamViewPagerAdapter = new TeamViewPagerAdapter(getSupportFragmentManager());
        teamViewPagerAdapter.AddFragment(new FragmentTeamInfo(), "TEAM INFO");
        teamViewPagerAdapter.AddFragment(new FragmentTeamSquad(), "SQUAD");
        teamViewPagerAdapter.AddFragment(new FragmentTeamMatches(),"MATCHES");
        viewPager.setAdapter(teamViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.info_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.squad_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.matches_icon);
    }
}
