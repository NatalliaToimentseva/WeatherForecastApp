package com.example.weatherforecasts.dataSources.entities

import com.google.gson.annotations.SerializedName

data class Hour(
    val condition: Condition,
    @SerializedName("temp_c")
    val temp: Double,
    val time: String,
)