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

    @GET("discovery/v2/events.json")
    Call<JsonObject> getMusicEvents2(
            @Query("apikey") String apiKey,
            @Query("size") int size,
            @Query("page") int page);

    @GET("discovery/v2/events.json")
    Call<JsonObject> getEventsByGenre(
            @Query("apikey") String apiKey,
            @Query("classificationName") String classificationName,
            @Query("genre") String genre);

    @GET("discovery/v2/events.json")
    Call<JsonObject> getEventsByGenre2(
            @Query("apikey") String apiKey,
            @Query("classificationName") String classificationName,
            @Query("genre") String genre,
            @Query("size") int size,
            @Query("page") int page);

    @GET("discovery/v2/events.json")
    Call<JsonObject> getAllMusicEvents(                        //          ************  ok  ************
            @Query("apikey") String apiKey,
            @Query("classificationName") String classificationName,
            @Query("page") int page);
    @GET("discovery/v2/events.json")
    Call<JsonObject> getEventsByGenre3(                       //         *************  ok **************
            @Query("apikey") String apiKey,
            @Query("classificationName") String classificationName,
            @Query("subGenre") String subGenre,
            @Query("page") int page);
    @GET("discovery/v2/attractions.json")
    Call<JsonObject> getMusicEvents(
            @Query("keyword") String keyword,
            @Query("apikey") String apiKey
    );

    @GET("discovery/v2/events.json")
    Call<JsonObject> getMusicEvents1(
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
