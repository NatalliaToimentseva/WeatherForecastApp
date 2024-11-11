package com.example.weatherforecasts.repository

interface WeatherDataRepository {

    suspend fun loadWeatherDate(location: String)
}