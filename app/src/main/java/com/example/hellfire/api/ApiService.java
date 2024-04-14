package com.example.hellfire.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("discovery/v2/events.json")
    Call<JsonObject> getEvents(
            @Query("keyword") String keyword,
            @Query("apikey") String apiKey
    );

    @GET("discovery/v2/attractions.json")
    Call<JsonObject> getMusicEvents(            //          ************  ok  ************
            @Query("keyword") String keyword,
            @Query("apikey") String apiKey
    );

    @GET("discovery/v2/events.json")
    Call<JsonObject> getMusicEvents1(          //         *************  ok **************
            @Query("keyword") String keyword,
            @Query("apikey") String apiKey
    );
    @GET("discovery/v2/events.json")
    Call<JsonObject> getAllMusicEvents(
           // @Query("keyword") String keyword,
            @Query("apikey") String apiKey
    );

    @GET("discovery/v2/events.json")
    Call<JsonObject> getEvents(
            // @Query("keyword") String keyword,
            @Query("apikey") String apiKey
    );
}
