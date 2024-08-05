package com.example.weatherforecasts.dataSources.entities

import com.google.gson.annotations.SerializedName

data class Day(
    val condition: Condition,
    @SerializedName("maxtemp_c")
    val maxTemp: Double,
    @SerializedName("mintemp_c")
    val minTemp: Double,
    @SerializedName("daily_chance_of_rain")
    val rainChance: Int
)