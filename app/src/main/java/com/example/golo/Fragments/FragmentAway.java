package com.example.golo.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.Models.Standing.Standing;
import com.example.Models.Standing.StandingTeam;
import com.example.golo.DataSource;
import com.example.golo.R;
import com.example.golo.RecyclerViewStandingAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAway extends Fragment{
    View view;
    private RecyclerView recyclerView;
    private Standing standing;
    private List<StandingTeam> standingTeamList;
    DataSource<Standing> dataStanding = new DataSource<>();

    public FragmentAway(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceBundle){
        view = inflater.inflate(R.layout.away_fragment, container, false);
        recyclerView = view.findViewById(R.id.awayStandingRecyclerView);
        RecyclerViewStandingAdapter recyclerViewStandingAdapter = new RecyclerViewStandingAdapter(getActivity(),standingTeamList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewStandingAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        String compId = getArguments().getString("compId");
        try {
            standing = dataStanding.getObjectfromJson("http://api.football-data.org/v2/competitions/"+compId+"/standings", Standing.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        standingTeamList = new ArrayList<>(standing.getStandings().get(2).getTable());
    }
}
