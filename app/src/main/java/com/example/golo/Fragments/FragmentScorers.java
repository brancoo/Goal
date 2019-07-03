package com.example.golo.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.Models.Player.Scorer;
import com.example.Models.Player.ScoringList;
import com.example.Models.Standing.Standing;
import com.example.golo.DataSource;
import com.example.golo.R;
import com.example.golo.RecyclerViewScorersAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentScorers extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private ScoringList scoringList;
    private DataSource<ScoringList> dataScoringList = new DataSource<>();
    private List<Scorer> scorerList = new ArrayList<>();

    public FragmentScorers(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceBundle){
        view = inflater.inflate(R.layout.scorers_fragment, container, false);
        recyclerView = view.findViewById(R.id.scorersRecyclerView);
        RecyclerViewScorersAdapter recyclerViewStandingAdapter = new RecyclerViewScorersAdapter(getActivity(),scorerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewStandingAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        String compId = getArguments().getString("compId");
        try {
            scoringList = dataScoringList.getObjectfromJson("http://api.football-data.org/v2/competitions/"+compId+"/scorers", ScoringList.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        scorerList = new ArrayList<>(scoringList.getScorers());
    }
}