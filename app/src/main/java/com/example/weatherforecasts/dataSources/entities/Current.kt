package com.example.weatherforecasts.dataSources.entities

import com.google.gson.annotations.SerializedName

data class Current(
    val condition: Condition,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("temp_c")
    val tempC: Double
)