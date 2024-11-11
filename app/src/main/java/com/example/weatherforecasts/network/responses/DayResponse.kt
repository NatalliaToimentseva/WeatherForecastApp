package com.example.weatherforecasts.network.responses

import com.google.gson.annotations.SerializedName

data class DayResponse(
    @SerializedName("condition")
    val condition: ConditionResponse,
    @SerializedName("maxtemp_c")
    val maxTemp: Double,
    @SerializedName("mintemp_c")
    val minTemp: Double,
    @SerializedName("daily_chance_of_rain")
    val rainChance: Int
)