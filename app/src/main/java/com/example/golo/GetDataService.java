package com.example.golo;

import com.example.Models.Competition.Competition;
import com.example.Models.Competition.CompetitionList;
import com.example.Models.Match.MatchList;
import com.example.Models.Match.SingleMatch;
import com.example.Models.Player.Scorer;
import com.example.Models.Player.ScoringList;
import com.example.Models.Standing.Standing;
import com.example.Models.Team.Team;
import com.example.Models.Team.TeamList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetDataService {
    @GET("competitions")
    Call<CompetitionList> getCompetitions();

    @GET("competitions/{id}")
    Call<Competition> getCompetition(@Path("id") String id);

    @GET("competitions/{id}/standings")
    Call<Standing> getStandings(@Path("id") String id);

    @GET("competitions/{id}/teams")
    Call<TeamList> getTeams(@Path("id") String id);

    @GET("teams/{teamId}")
    Call<Team> getTeam(@Path("teamId") String teamId);

    @GET("competitions/{id}/matches")
    Call<MatchList> getMatches(@Path("id") String id);

    @GET("matches/{matchId}")
    Call<SingleMatch> getSingleMatch(@Path("matchId") String matchId);

    @GET("competitions/{id}/scorers")
    Call<ScoringList> getScorers(@Path("id") String id);
}