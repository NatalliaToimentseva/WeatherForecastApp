package com.example.weatherforecasts.ui.hoursForecastScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecasts.databinding.FragmentHoursBinding
import com.example.weatherforecasts.models.WeatherModel
import com.example.weatherforecasts.ui.adaptor.RwWeatherAdapter
import com.example.weatherforecasts.ui.homeScreen.HomeViewModel


class HoursFragment : Fragment() {

    private var binding: FragmentHoursBinding? = null
    private var adapter: RwWeatherAdapter? = null
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHoursBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.hoursForecast.observe(viewLifecycleOwner) {
            initRcView(it)
        }
        viewModel.getHoursForecast()
    }

    private fun initRcView(hoursForecastList: List<WeatherModel>) {
        binding?.run {
            rvListWeatherHours.layoutManager = LinearLayoutManager(requireActivity())
            adapter = RwWeatherAdapter().also {
                rvListWeatherHours.adapter = it
            }
            adapter?.submitList(hoursForecastList)
        }
    }
}