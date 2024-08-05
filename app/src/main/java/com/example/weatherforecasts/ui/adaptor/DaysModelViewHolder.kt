package com.example.weatherforecasts.ui.adaptor

import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecasts.R
import com.example.weatherforecasts.databinding.DaysItemBinding
import com.example.weatherforecasts.ui.models.DaysForecastModel
import com.example.weatherforecasts.utils.load

class DaysModelViewHolder(private var binding: DaysItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DaysForecastModel) {
        binding.run {
            tvDay.text = item.date
            tvConditionDays.text = item.condition
            tvTempDays.text = root.context.getString(
                R.string.dateHours,
                item.maxTemp,
                item.mintTemp
            )
            ivDays.load(item.imageUrl)
            rainChance.text =
                root.context.getString(R.string.rain_field, item.rainChance)
        }
    }
}