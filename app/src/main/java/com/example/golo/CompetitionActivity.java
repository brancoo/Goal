package com.example.golo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.example.Models.Competition.Competition;
import com.example.Models.Standing.Standing;
import com.google.android.material.tabs.TabLayout;

public class CompetitionActivity extends AppCompatActivity {
    private final String url = "http://api.football-data.org/v2/competitions/";
    private Competition competition;
    private Standing standing;
    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Bundle extras = getIntent().getExtras();
        String compId = extras.getString("compId");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {  //verificar se o objecto seleccionado já existe para não fazer request sempre do mesmo objecto
            DataSource<Competition> data = new DataSource<>();
            DataSource<Standing> dataStanding = new DataSource<>();
            competition = data.getObjectfromJson(url + compId, Competition.class);
            standing = dataStanding.getObjectfromJson(url+"standings", Standing.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getSupportActionBar().setTitle("\t"+competition.getName());
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

        tabLayout = findViewById(R.id.tabLayoutId);
        viewPager = findViewById(R.id.viewPagerId);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new FragmentTotal(),"TOTAL");
        viewPagerAdapter.AddFragment(new FragmentHome(),"HOME");
        viewPagerAdapter.AddFragment(new FragmentHome(),"AWAY");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
