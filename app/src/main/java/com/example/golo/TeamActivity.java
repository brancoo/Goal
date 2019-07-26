package com.example.golo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
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
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;

public class TeamActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private TeamViewPagerAdapter teamViewPagerAdapter;
    private ViewPager viewPager;
    private GetDataService apiService;
    private Team team;
    private MatchList matchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        Bundle extras = getIntent().getExtras();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("\t" + extras.getString("teamName"));

        final ProgressBar progressBar;
        progressBar = findViewById(R.id.progressBarTeamId);

        apiService = RetrofitClient.getRetrofitInstance().create(GetDataService.class);
        Call<Team> teamCall = apiService.getTeam(extras.getString("teamId"));
        teamCall.enqueue(new Callback<Team>() {
            @Override
            public void onResponse(Call<Team> call, Response<Team> responseTeam) {
                if (responseTeam.isSuccessful()) {
                    team = responseTeam.body();
                    apiService = RetrofitClient.getRetrofitInstance().create(GetDataService.class);
                    Call<MatchList> matchListCall = apiService.getMatches(extras.getString("compId"));
                    matchListCall.enqueue(new Callback<MatchList>() {
                        @Override
                        public void onResponse(Call<MatchList> call, Response<MatchList> responseMatch) {
                            if (responseMatch.isSuccessful()) {
                                matchList = responseMatch.body();
                                progressBar.setVisibility(View.GONE);

                                tabLayout = findViewById(R.id.tabLayoutTeamId);
                                viewPager = findViewById(R.id.teamViewPagerId);
                                teamViewPagerAdapter = new TeamViewPagerAdapter(getSupportFragmentManager());

                                FragmentTeamInfo fragmentTeamInfo = new FragmentTeamInfo();
                                extras.putSerializable("team", team);
                                fragmentTeamInfo.setArguments(extras);

                                FragmentTeamSquad fragmentTeamSquad = new FragmentTeamSquad();
                                ArrayList<Player> teamSquad = new ArrayList<>(team.getSquad().size());
                                teamSquad.addAll(team.getSquad());
                                extras.putSerializable("teamSquad", teamSquad);
                                fragmentTeamSquad.setArguments(extras);

                                FragmentTeamMatches fragmentTeamMatches = new FragmentTeamMatches();
                                ArrayList<Match> matches = new ArrayList<>();

                                for (int i = 0; i < matchList.getMatches().size(); i++) {
                                    if (matchList.getMatches().get(i).getAwayTeam().getName().equals(team.getName())
                                            || matchList.getMatches().get(i).getHomeTeam().getName().equals(team.getName()))
                                        matches.add(matchList.getMatches().get(i));
                                }
                                extras.putSerializable("teamMatches", matches);
                                fragmentTeamMatches.setArguments(extras);

                                teamViewPagerAdapter.AddFragment(fragmentTeamInfo, getString(R.string.team_info));
                                teamViewPagerAdapter.AddFragment(fragmentTeamSquad, getString(R.string.team_squad));
                                teamViewPagerAdapter.AddFragment(fragmentTeamMatches, getString(R.string.team_matches));
                                viewPager.setAdapter(teamViewPagerAdapter);
                                tabLayout.setupWithViewPager(viewPager);

                                tabLayout.getTabAt(0).setIcon(R.drawable.info_icon);
                                tabLayout.getTabAt(0).getIcon().setTint(Color.WHITE);
                                tabLayout.getTabAt(1).setIcon(R.drawable.squad_icon);
                                tabLayout.getTabAt(1).getIcon().setTint(Color.WHITE);
                                tabLayout.getTabAt(2).setIcon(R.drawable.matches_icon);
                                tabLayout.getTabAt(2).getIcon().setTint(Color.WHITE);
                            }else{
                                try {
                                    JSONObject jObjError = new JSONObject(responseMatch.errorBody().string());
                                    Toast.makeText(TeamActivity.this,"HAVE TO WAIT: " + jObjError.getString("message").substring(37,39)+ " seconds!", Toast.LENGTH_SHORT).show();
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MatchList> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }else{
                    try {
                        JSONObject jObjError = new JSONObject(responseTeam.errorBody().string());
                        Toast.makeText(TeamActivity.this,"HAVE TO WAIT: " + jObjError.getString("message").substring(37,39)+ " seconds!", Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<Team> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}
