package com.example.golo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentAway extends Fragment {
    View view;
    public FragmentAway(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceBundle){
        view = inflater.inflate(R.layout.away_fragment, container, false);
        return view;
    }
}
