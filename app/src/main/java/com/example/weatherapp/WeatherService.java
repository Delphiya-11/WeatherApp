package com.example.weatherapp;

import pojo.WeatherData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("data/2.5/weather?")
    Call<WeatherResponse> getCurrentWeatherData(@Query("lat") String lat, @Query("lon") String lon, @Query("APPID") String APPID);

    @GET("data/2.5/weather?")
    Call<WeatherResponse> getCurrentWeatherDataC(@Query("q") String city, @Query("units") String unit, @Query("APPID") String APPID);

    @GET("data/2.5/forecast?")
    Call<WeatherData> getFutureWeatherData(@Query("lat") String lat, @Query("lon") String lon, @Query("APPID") String APPID);


    //api.openweathermap.org/data/2.5/forecast?q=London,us&mode=xml
    //api.openweathermap.org/data/2.5/weather?q=London
    //api.openweathermap.org/data/2.5/forecast?id=524901
}
