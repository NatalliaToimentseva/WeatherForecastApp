package com.example.weatherforecasts.network.responses

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("current")
    val current: CurrentResponse,
    @SerializedName("forecast")
    val forecast: ForecastResponse,
    @SerializedName("location")
    val location: LocationResponse
)