package com.example.golo;

import com.example.Models.Competition.CompetitionList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("competitions")
    Call<CompetitionList> getCompetitions();
}