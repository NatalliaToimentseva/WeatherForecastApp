package com.example.weatherforecasts.network.responses

import com.google.gson.annotations.SerializedName

data class CurrentResponse(
    @SerializedName("condition")
    val condition: ConditionResponse,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("temp_c")
    val tempC: Double
)