package com.example.weatherforecasts.ui.adaptor

import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecasts.databinding.ListItemBinding
import com.example.weatherforecasts.models.CurrentDayModel
import com.example.weatherforecasts.models.DaysForecastModel
import com.example.weatherforecasts.models.HoursForecastModel
import com.example.weatherforecasts.models.Weather
import com.squareup.picasso.Picasso

class WeatherModelViewHolder(private var binding: ListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Weather) {

        when (item) {
            is HoursForecastModel -> binding.run {
                tvDataTimeDayHours.text = item.time
                tvConditionDayHours.text = item.condition
                ivTempDayHours.text = item.currentTemp
                Picasso.get().load("https:" + item.imageUrl).into(ivDayHours)
            }

            is DaysForecastModel ->
                binding.run {
                    tvDataTimeDayHours.text = item.date
                    tvConditionDayHours.text = item.condition
                    ivTempDayHours.text = "${item.maxTemp}C/${item.mintTemp}C"
                    Picasso.get().load("https:" + item.imageUrl).into(ivDayHours)
                }

            is CurrentDayModel -> {}
        }
    }
}