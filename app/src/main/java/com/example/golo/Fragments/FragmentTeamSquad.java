package com.example.golo.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.Models.Player.Player;
import com.example.golo.R;
import com.example.golo.RecyclerViewSquadAdapter;

import java.util.ArrayList;

public class FragmentTeamSquad extends Fragment {
    private View v;
    private RecyclerView recyclerView;
    private ArrayList<Player> teamSquad;

    public FragmentTeamSquad(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.teamsquad_fragment, container, false);
        recyclerView = v.findViewById(R.id.squadRecyclerViewId);
        RecyclerViewSquadAdapter recyclerViewStandingAdapter = new RecyclerViewSquadAdapter(getActivity(),teamSquad);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewStandingAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        teamSquad = ((ArrayList<Player>) getArguments().getSerializable("teamSquad"));
    }
}
