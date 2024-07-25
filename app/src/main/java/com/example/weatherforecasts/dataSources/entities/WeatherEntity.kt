package com.example.weatherforecasts.dataSources.entities

data class WeatherEntity(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)