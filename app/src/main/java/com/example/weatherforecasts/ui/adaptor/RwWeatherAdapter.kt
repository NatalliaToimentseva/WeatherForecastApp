package com.example.weatherforecasts.ui.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.weatherforecasts.databinding.DaysItemBinding
import com.example.weatherforecasts.databinding.HoursItemBinding
import com.example.weatherforecasts.ui.models.DaysForecastModel
import com.example.weatherforecasts.ui.models.Forecast
import com.example.weatherforecasts.ui.models.HoursForecastModel

class RwWeatherAdapter : ListAdapter<Forecast, ViewHolder>(WeatherDiffUtils()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HoursForecastModel -> ViewHolderType.Hours.id
            is DaysForecastModel -> ViewHolderType.Days.id
            else -> ViewHolderType.Current.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ViewHolderType.Hours.id -> HoursModelViewHolder(
                HoursItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ViewHolderType.Days.id -> DaysModelViewHolder(
                DaysItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> {
                val recyclerView = object : ViewHolder(FrameLayout(parent.context)) {}
                recyclerView
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is HoursForecastModel -> {
                (holder as? HoursModelViewHolder)?.bind(item)
            }

            is DaysForecastModel -> {
                (holder as? DaysModelViewHolder)?.bind(item)
            }
        }
    }
}