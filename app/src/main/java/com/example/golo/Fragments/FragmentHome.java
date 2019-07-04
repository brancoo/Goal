package com.example.golo.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.Models.Standing.StandingType;
import com.example.golo.R;
import com.example.golo.RecyclerViewStandingAdapter;

import java.util.ArrayList;

public class FragmentHome extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private ArrayList<StandingType> standingTeamList;

    public FragmentHome(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceBundle){
        view = inflater.inflate(R.layout.home_fragment, container, false);
        recyclerView = view.findViewById(R.id.homeStandingRecyclerView);
        RecyclerViewStandingAdapter recyclerViewStandingAdapter = new RecyclerViewStandingAdapter(getActivity(), standingTeamList.get(1).getTable());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewStandingAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        standingTeamList = ((ArrayList<StandingType>) getArguments().getSerializable("standings"));

    }
}
