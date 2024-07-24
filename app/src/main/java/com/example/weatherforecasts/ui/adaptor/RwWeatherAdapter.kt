package com.example.weatherforecasts.ui.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.weatherforecasts.databinding.ListItemBinding
import com.example.weatherforecasts.models.Weather

class RwWeatherAdapter : ListAdapter<Weather, WeatherModelViewHolder>(WeatherDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherModelViewHolder {
        return WeatherModelViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WeatherModelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}