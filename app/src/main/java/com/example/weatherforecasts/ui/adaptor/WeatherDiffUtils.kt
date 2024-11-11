package com.example.weatherforecasts.ui.adaptor

import androidx.recyclerview.widget.DiffUtil
import com.example.weatherforecasts.ui.models.Forecast

class WeatherDiffUtils : DiffUtil.ItemCallback<Forecast>() {

    override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
        return false
    }
}