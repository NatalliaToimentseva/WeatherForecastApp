package com.example.weatherforecasts.network.responses

import com.google.gson.annotations.SerializedName

data class HourResponse(
    @SerializedName("condition")
    val condition: ConditionResponse,
    @SerializedName("temp_c")
    val temp: Double,
    @SerializedName("time")
    val time: String,
)