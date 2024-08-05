package com.example.weatherforecasts.ui.adaptor

import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecasts.R
import com.example.weatherforecasts.databinding.HoursItemBinding
import com.example.weatherforecasts.ui.models.HoursForecastModel
import com.example.weatherforecasts.utils.load

class HoursModelViewHolder(private var binding: HoursItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: HoursForecastModel) {
        binding.run {
            tvHours.text = item.time
            tvConditionHours.text = item.condition
            tvTempHours.text =
                binding.root.context.getString(R.string.hoursTemp, item.currentTemp)
            ivHours.load(item.imageUrl)
        }
    }
}