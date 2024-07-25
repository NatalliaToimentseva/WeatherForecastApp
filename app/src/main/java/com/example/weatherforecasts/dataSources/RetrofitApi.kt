package com.example.weatherforecasts.dataSources

import com.example.weatherforecasts.dataSources.entities.WeatherEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET("forecast.json")
    suspend fun getWeatherData(
        @Query("key") key: String,
        @Query("q") location: String,
        @Query("days") days: String,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") al: String = "no"
    ) : Response<WeatherEntity>
}