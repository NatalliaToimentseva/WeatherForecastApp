package com.example.weatherforecasts.ui.hoursForecastScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecasts.databinding.FragmentHoursBinding
import com.example.weatherforecasts.localStorage.SharedPreferencesRepository
import com.example.weatherforecasts.models.HoursForecastModel
import com.example.weatherforecasts.ui.adaptor.RwWeatherAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HoursFragment : Fragment() {

    private var binding: FragmentHoursBinding? = null
    private var adapter: RwWeatherAdapter? = null
    private val viewModel: HoursViewModel by viewModels()
    @Inject
    lateinit var sharedPreferences: SharedPreferencesRepository

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
        sharedPreferences.getWeatherData()?.let { viewModel.getHoursForecast(it) }
    }

    private fun initRcView(hoursForecastList: List<HoursForecastModel>) {
        binding?.run {
            rvListWeatherHours.layoutManager = LinearLayoutManager(requireActivity())
            adapter = RwWeatherAdapter().also {
                rvListWeatherHours.adapter = it
            }
            adapter?.submitList(hoursForecastList)
        }
    }
}