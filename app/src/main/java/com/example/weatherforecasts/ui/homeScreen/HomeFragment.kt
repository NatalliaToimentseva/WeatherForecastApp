package com.example.weatherforecasts.ui.homeScreen

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.weatherforecasts.R
import com.example.weatherforecasts.constants.CITY
import com.example.weatherforecasts.databinding.FragmentHomeBinding
import com.example.weatherforecasts.localStorage.SharedPreferencesRepository
import com.example.weatherforecasts.models.CurrentDayModel
import com.example.weatherforecasts.ui.daysForecastScreen.DaysFragment
import com.example.weatherforecasts.ui.homeScreen.adapter.VpAdapter
import com.example.weatherforecasts.ui.hoursForecastScreen.HoursFragment
import com.example.weatherforecasts.utils.isPermissionGranted
import com.example.weatherforecasts.utils.makeToast
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private var pLauncher: ActivityResultLauncher<String>? = null

    @Inject
    lateinit var sharedPreferences: SharedPreferencesRepository
    private val fragmentsList = listOf(
        HoursFragment(),
        DaysFragment(),
        DaysFragment()
    )
    private val tabTitles = listOf(
        "Hours",
        "3-days",
        "10-days"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.weatherData.observe(viewLifecycleOwner) { data ->
            data?.run {
                sharedPreferences.setWeatherData(this)
                viewModel.getParsedCurrentDayWeather(this)
            }
        }
        viewModel.currentDayWeather.observe(viewLifecycleOwner) {
            updateCurrentCard(it)
        }
        viewModel.errorsGettingData.observe(viewLifecycleOwner) { error ->
            binding?.run {
                if (error != null) {
                    dayCard.visibility = View.GONE
                    tvError.visibility = View.VISIBLE
                    tvError.text = getString(R.string.home_screen_error)
                    requireActivity().makeToast(error)
                    viewModel.clearError()
                }
            }
        }
        checkPermission()
        init()

        if (isInternetConnection()) {
            viewModel.getWeatherData(requireActivity(), CITY)
        } else viewModel.setError("No internet connection")
    }

    private fun init() = binding?.run {
        val adapter = VpAdapter(this@HomeFragment, fragmentsList)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    private fun isInternetConnection(): Boolean {
        val cm =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetwork != null
    }

    private fun permissionListener() {
        pLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {

        }
    }

    private fun checkPermission() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListener()
            pLauncher?.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun updateCurrentCard(item: CurrentDayModel) {
        val mxMinTemp = "${item.maxTemp}C/${item.mintTemp}C"
        binding?.run {
            tvData.text = item.dateTime
            tvCity.text = item.city
            tvCurrentTemp.text = item.currentTemp
            tvCondition.text = item.condition
            tvMaxMin.text = mxMinTemp
            Picasso.get().load("https:" + item.imageUrl).into(imWeather)
        }
    }
}