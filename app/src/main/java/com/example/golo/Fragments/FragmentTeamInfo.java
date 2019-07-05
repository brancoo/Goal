package com.example.golo.Fragments;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.Models.Team.Team;
import com.example.golo.R;
import com.squareup.picasso.Picasso;

import java.io.File;

public class FragmentTeamInfo extends Fragment {
    private View v;
    private Team team;

    public FragmentTeamInfo(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.teaminfo_fragment, container, false);
        team = (Team) getArguments().getSerializable("team");

        ImageView teamLogo = v.findViewById(R.id.teamLogoId);
        Picasso.get().load(team.getCrestUrl()).into(teamLogo);

        TextView teamAlias = v.findViewById(R.id.teamAliasId);
        teamAlias.setText("Alias: " + team.getTla());

        TextView teamYearFoundation = v.findViewById(R.id.teamFoundationId);
        teamYearFoundation.setText("Year of Foundation: " + team.getFounded());

        TextView teamActiveCompetitions = v.findViewById(R.id.teamActiveCompetitionsId);
        if(team.getActiveCompetitions().isEmpty())
            teamActiveCompetitions.setText("Active Competitions: NONE");
        else{
            for(int i = 0; i < team.getActiveCompetitions().size(); i++)
            teamActiveCompetitions.setText("Active Competition(s): "+ team.getActiveCompetitions().get(i).getName());
        }

        TextView teamStadium = v.findViewById(R.id.teamStadiumId);
        teamStadium.setText("Stadium: " + team.getVenue());

        TextView teamColours = v.findViewById(R.id.teamColoursId);
        teamColours.setText("Team Colours: "+ team.getClubColors());

        TextView teamWebsite = v.findViewById(R.id.teamWebsiteId);
        teamWebsite.setText("Team's Website: " + team.getWebsite());
        return v;
    }
}
