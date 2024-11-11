package com.example.weatherforecasts.network.responses

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("name")
    val name: String
)