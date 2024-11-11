package com.example.weatherforecasts.network

import com.example.weatherforecasts.constants.NO_QUERY
import com.example.weatherforecasts.network.responses.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET("forecast.json")
    suspend fun getWeatherData(
        @Query("key") key: String,
        @Query("q") location: String,
        @Query("days") days: String,
        @Query("aqi") aqi: String = NO_QUERY,
        @Query("alerts") al: String = NO_QUERY
    ) : Response<WeatherResponse>
}