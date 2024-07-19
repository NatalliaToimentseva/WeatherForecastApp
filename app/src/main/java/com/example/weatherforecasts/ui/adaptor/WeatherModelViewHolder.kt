package com.example.weatherforecasts.ui.adaptor

import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecasts.databinding.ListItemBinding
import com.example.weatherforecasts.models.WeatherModel
import com.squareup.picasso.Picasso

class WeatherModelViewHolder(private var binding: ListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WeatherModel) {
        binding.run {
            tvDataTimeDayHours.text = item.time
            tvConditionDayHours.text = item.condition
            ivTempDayHours.text = item.currentTemp
            Picasso.get().load("https:" + item.imageUrl).into(ivDayHours)
        }
    }
}