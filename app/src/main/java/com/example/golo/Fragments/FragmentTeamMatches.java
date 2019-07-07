package com.example.golo.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.Models.Match.Match;
import com.example.golo.R;
import com.example.golo.RecyclerViewMatchesAdapter;
import com.example.golo.RecyclerViewStandingAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentTeamMatches extends Fragment {
    private View v;
    private RecyclerView recyclerView;
    private ArrayList<Match> matchList;

    public FragmentTeamMatches(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.teammatches_fragment, container,false);
        recyclerView = v.findViewById(R.id.matchesRecyclerViewId);
        RecyclerViewMatchesAdapter recyclerViewMatchesAdapter = new RecyclerViewMatchesAdapter(getActivity(), matchList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setAdapter(recyclerViewMatchesAdapter);
        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        matchList = (ArrayList<Match>) getArguments().getSerializable("teamMatches");
    }
}
