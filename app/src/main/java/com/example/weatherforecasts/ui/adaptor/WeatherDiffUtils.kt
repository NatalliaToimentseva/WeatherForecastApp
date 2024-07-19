package com.example.weatherforecasts.ui.adaptor

import androidx.recyclerview.widget.DiffUtil
import com.example.weatherforecasts.models.WeatherModel

class WeatherDiffUtils : DiffUtil.ItemCallback<WeatherModel>() {
    override fun areItemsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
        return oldItem == newItem
    }
}