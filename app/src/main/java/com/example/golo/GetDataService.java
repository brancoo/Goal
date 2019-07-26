package com.example.golo;

import com.example.Models.Competition.Competition;
import com.example.Models.Competition.CompetitionList;
import com.example.Models.Match.MatchList;
import com.example.Models.Match.SingleMatch;
import com.example.Models.Player.ScoringList;
import com.example.Models.Standing.Standing;
import com.example.Models.Team.Team;
import com.example.Models.Team.TeamList;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetDataService {
    @GET("competitions")
    Observable<CompetitionList> getCompetitions();

    @GET("competitions/{id}")
    Observable<Competition> getCompetition(@Path("id") String id);

    @GET("competitions/{id}/standings")
    Observable<Standing> getStandings(@Path("id") String id);

    @GET("competitions/{id}/teams")
    Observable<TeamList> getTeams(@Path("id") String id);

    @GET("teams/{teamId}")
    Observable<Team> getTeam(@Path("teamId") String teamId);

    @GET("competitions/{id}/matches")
    Observable<MatchList> getMatches(@Path("id") String id);

    @GET("matches/{matchId}")
    Observable<SingleMatch> getSingleMatch(@Path("matchId") String matchId);

    @GET("competitions/{id}/scorers")
    Observable<ScoringList> getScorers(@Path("id") String id);
}