package com.example.weatherforecasts.ui.adaptor

import androidx.recyclerview.widget.DiffUtil
import com.example.weatherforecasts.models.Weather

class WeatherDiffUtils : DiffUtil.ItemCallback<Weather>() {

    override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return false
    }
}