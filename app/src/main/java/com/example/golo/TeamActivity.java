package com.example.golo;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class TeamActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private TeamViewPagerAdapter teamViewPagerAdapter;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private Team team;
    private MatchList matchList;
    private GetDataService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        Bundle extras = getIntent().getExtras();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("\t" + extras.getString("teamName"));

        apiService = RetrofitClient.getRetrofitInstance().create(GetDataService.class);
        Call<Team> teamCall = apiService.getTeam(extras.getString("teamId"));
        teamCall.enqueue(new Callback<Team>() {

            @Override
            public void onResponse(Call<Team> call, Response<Team> responseTeam) {
                apiService = RetrofitClient.getRetrofitInstance().create(GetDataService.class);
                Call<MatchList> matchListCall = apiService.getMatches(extras.getString("compId"));
                matchListCall.enqueue(new Callback<MatchList>() {

                    @Override
                    public void onResponse(Call<MatchList> call, Response<MatchList> responseMatchList) {
                        tabLayout = findViewById(R.id.tabLayoutTeamId);
                        viewPager = findViewById(R.id.teamViewPagerId);
                        teamViewPagerAdapter = new TeamViewPagerAdapter(getSupportFragmentManager());

                        FragmentTeamInfo fragmentTeamInfo = new FragmentTeamInfo();
                        extras.putSerializable("team", responseTeam.body());
                        fragmentTeamInfo.setArguments(extras);

                        FragmentTeamSquad fragmentTeamSquad = new FragmentTeamSquad();
                        ArrayList<Player> teamSquad = new ArrayList<>(responseTeam.body().getSquad().size());
                        teamSquad.addAll(responseTeam.body().getSquad());
                        extras.putSerializable("teamSquad", teamSquad);
                        fragmentTeamSquad.setArguments(extras);

                        FragmentTeamMatches fragmentTeamMatches = new FragmentTeamMatches();
                        ArrayList<Match> matches = new ArrayList<>();

                        for(int i = 0; i < responseMatchList.body().getMatches().size(); i++){
                            if(responseMatchList.body().getMatches().get(i).getAwayTeam().getName().equals(responseTeam.body().getName())
                                    || responseMatchList.body().getMatches().get(i).getHomeTeam().getName().equals(responseTeam.body().getName()))
                                matches.add(responseMatchList.body().getMatches().get(i));
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
                    
                    @Override
                    public void onFailure(Call<MatchList> call, Throwable t) {
                        t.printStackTrace();
                        Log.d("ERRO", "ERRO NA MATCHLIST");
                    }
                });
            }
            @Override
            public void onFailure(Call<Team> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
