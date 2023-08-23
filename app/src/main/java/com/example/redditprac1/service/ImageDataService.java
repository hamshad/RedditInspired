package com.example.redditprac1.service;


import com.example.redditprac1.model.ProgrammerHumor;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ImageDataService {

//    @GET("r/ProgrammerHumor/top.json?limit=50&amp;t=month")
    @GET("r/{subred}/top.json")
    Call<ProgrammerHumor> getData(@Path("subred") String reddit, @Query("limit") int doseLimit, @Query("t") String doseTop);

    

}
