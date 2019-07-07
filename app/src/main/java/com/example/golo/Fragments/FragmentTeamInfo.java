package com.example.golo.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.Models.Team.Team;
import com.example.golo.R;
import com.example.golo.Utils;
import com.squareup.picasso.Picasso;

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
        teamLogo.setVisibility(ImageView.INVISIBLE);
        String logoUrl = team.getCrestUrl();

        if(logoUrl != null){
            if(logoUrl.contains(".svg")) {
                Utils.fetchSvg(getContext(), team.getCrestUrl(), teamLogo);
                teamLogo.setVisibility(ImageView.VISIBLE);
            }
            else if(logoUrl.contains(".png")) {
                teamLogo.setVisibility(ImageView.VISIBLE);
                Picasso.get().load(team.getCrestUrl()).fit().noFade().into(teamLogo);
            }
        }

        if(teamLogo.getVisibility() == View.INVISIBLE){
            Toast.makeText(inflater.getContext(),"NAO FOI POSSIVEL CARREGAR IMAGEM", Toast.LENGTH_LONG).show();
        }

        teamLogo.setPadding(0, 100, 0, 0);
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

        if(team.getClubColors() != null) {
            String[] colours = team.getClubColors().split(" / ");
            TextView teamColoursFirst = v.findViewById(R.id.teamColoursFirstId);
            teamColoursFirst.setText("Main Colours: " + colours[0] + " - ");

            TextView teamColoursSecond = v.findViewById(R.id.teamColoursSecondId);
            teamColoursSecond.setText(colours[1]);

            if (colours.length == 3) {
                TextView teamColoursThird = v.findViewById(R.id.teamColoursThirdId);
                teamColoursThird.setVisibility(TextView.VISIBLE);
                teamColoursThird.setText(" - " + colours[2]);
            }
        }

        TextView teamStadium = v.findViewById(R.id.teamStadiumId);
        teamStadium.setText("Stadium: " + team.getVenue());

        TextView teamWebsite = v.findViewById(R.id.teamWebsiteId);
        teamWebsite.setText("Team's Website: " + team.getWebsite());
        return v;
    }
}
