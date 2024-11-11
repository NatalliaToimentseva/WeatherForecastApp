package com.example.weatherforecasts.network.responses

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("forecastday")
    val forecastDay: List<ForecastdayRsponse>
)