package com.example.golo.Fragments;

import android.os.Bundle;
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
import com.example.golo.Utils;
import com.pixplicity.sharp.SvgParseException;
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
        String logoUrl = team.getCrestUrl();

        if(logoUrl != null){
            if(logoUrl.contains(".png")) {
                Picasso.get().load(team.getCrestUrl()).fit().noFade().into(teamLogo);
                teamLogo.setPadding(0, 100, 0, 0);
            }
            else if(logoUrl.contains(".svg")) {
                try {
                    Utils.fetchSvg(getContext(), team.getCrestUrl(), teamLogo);
                    teamLogo.setPadding(0, 100, 0, 0);
                }catch (SvgParseException e){
                    //Toast.makeText(getActivity(),"Text!",Toast.LENGTH_SHORT).show(); DOESN'T WORK
                }
            }
        }

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

        String[] colours = team.getClubColors().split(" / ");
        TextView teamColoursFirst = v.findViewById(R.id.teamColoursFirstId);
        teamColoursFirst.setText("Main Colours: " + colours[0] + " - ");

        TextView teamColoursSecond = v.findViewById(R.id.teamColoursSecondId);
        teamColoursSecond.setText(colours[1]);

        if(colours.length == 3){
            TextView teamColoursThird = v.findViewById(R.id.teamColoursThirdId);
            teamColoursThird.setVisibility(TextView.VISIBLE);
            teamColoursThird.setText(" - " + colours[2]);
        }

        TextView teamStadium = v.findViewById(R.id.teamStadiumId);
        teamStadium.setText("Stadium: " + team.getVenue());


        TextView teamWebsite = v.findViewById(R.id.teamWebsiteId);
        teamWebsite.setText("Team's Website: " + team.getWebsite());
        return v;
    }
}
