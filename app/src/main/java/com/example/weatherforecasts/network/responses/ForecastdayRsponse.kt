package com.example.weatherforecasts.network.responses

import com.google.gson.annotations.SerializedName

data class ForecastdayRsponse(
    @SerializedName("date")
    val date: String,
    @SerializedName("day")
    val day: DayResponse,
    @SerializedName("hour")
    val hour: List<HourResponse>
)