package com.example.weatherforecasts.ui.daysForecastScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecasts.databinding.FragmentDaysBinding
import com.example.weatherforecasts.ui.models.DaysForecastModel
import com.example.weatherforecasts.ui.adaptor.RwWeatherAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DaysFragment : Fragment() {

    private val viewModel: DaysForecastViewModel by viewModels()
    private var binding: FragmentDaysBinding? = null
    private var adapter: RwWeatherAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.daysForecast.observe(viewLifecycleOwner) {
            initRcView(it)
        }
    }

    private fun initRcView(daysForecastList: List<DaysForecastModel>) {
        binding?.run {
            rvListWeatherDays.layoutManager = LinearLayoutManager(requireActivity())
            adapter = RwWeatherAdapter().also {
                rvListWeatherDays.adapter = it
            }
            adapter?.submitList(daysForecastList)
        }
    }
}