package com.example.weatherforecasts.dataSources.entities

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("forecastday")
    val forecastDay: List<Forecastday>
)