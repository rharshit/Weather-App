package com.rharshit.weather.api;

import com.rharshit.weather.City;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiHandler {

    @GET("/api/location/search/")
    Call<List<City>> getCities(@Query("query") String query);

}
