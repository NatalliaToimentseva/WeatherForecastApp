package com.example.weatherforecasts.ui.homeScreen

import android.Manifest
import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherforecasts.R
import com.example.weatherforecasts.constants.CITY
import com.example.weatherforecasts.constants.SCHEME
import com.example.weatherforecasts.databinding.FragmentHomeBinding
import com.example.weatherforecasts.ui.daysForecastScreen.DaysFragment
import com.example.weatherforecasts.ui.homeScreen.adapter.VpAdapter
import com.example.weatherforecasts.ui.hoursForecastScreen.HoursFragment
import com.example.weatherforecasts.ui.models.CurrentDayModel
import com.example.weatherforecasts.utils.isPermissionGranted
import com.example.weatherforecasts.utils.makeToast
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

/**
 * HomeFragment is a Fragment that displays the weather information for a specific location.
 * It retrieves weather data based on the user's location or a specified city and presents it
 * through a user interface that includes tabs for hourly and daily forecasts.
 *
 * This fragment handles permissions for location access, checks for internet connectivity,
 * and updates the UI based on the weather data received from the ViewModel.
 *
 * The fragment uses the Android Jetpack components, including ViewModel, LiveData, and
 * Activity Result APIs for handling permissions.
 */

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private val fragmentsList = listOf(
        HoursFragment(),
        DaysFragment()
    )
    private val tabTitles = listOf(
        R.string.tabs_hours,
        R.string.tabs_3_days
    )
    private val permissions = arrayListOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private var binding: FragmentHomeBinding? = null
    private var pLauncher: ActivityResultLauncher<Array<String>>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentDayWeather.observe(viewLifecycleOwner) {
            showHome() //Shows app main screen
            updateCurrentCard(it)
        }
        viewModel.errorsGettingData.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                showError(error, R.string.home_screen_error) //Shows error screen, button "try again" and hide app main screen
                viewModel.clearError()
            }
        }
        viewModel.isInProgress.observe(viewLifecycleOwner) { isProgress ->
            binding?.run {
                if (isProgress) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                }
            }
        }
        checkPermission(permissions)
        init()
        binding?.tryAgain?.setOnClickListener {
            getDate()
        }
        binding?.run {
            ibSync.setOnClickListener {
                tabLayout.selectTab(tabLayout.getTabAt(0))
                getDate()
            }
        }
        binding?.ibSearch?.setOnClickListener {
            showDialog(
                getString(R.string.dialog_title),
                getString(R.string.dialog_message)
            ) { city ->
                if (isInternetConnection()) {
                    viewModel.getWeatherData(city)
                } else viewModel.setError(getString(R.string.no_internet))
            }
        }
    }

    /**
     * Initializes the ViewPager and TabLayout for displaying weather data in tabs.
     */
    private fun init() = binding?.run {
        val adapter = VpAdapter(this@HomeFragment, fragmentsList)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = requireContext().getString(tabTitles[position])
        }.attach()
    }

    /**
     * Checks if the required permissions are granted. If not, requests permissions from the user.
     *
     * @param permission A list of permissions to check.
     */
    private fun checkPermission(permission: List<String>) {
        if (!isPermissionGranted(permission)) {
            permissionListener()
            pLauncher?.launch(permission.toTypedArray())
        } else {
            getDate()
        }
    }

    /**
     * Registers a permission listener to handle the result of permission requests.
     */
    private fun permissionListener() {
        pLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            if (it.getValue(Manifest.permission.ACCESS_FINE_LOCATION) &&
                it.getValue(Manifest.permission.ACCESS_COARSE_LOCATION)
            ) {
                getDate()
            } else {
                if (isInternetConnection()) {
                    viewModel.getWeatherData(CITY)
                } else {
                    viewModel.setError(getString(R.string.no_internet))
                }
                requireContext().makeToast(getString(R.string.enable_permission))
            }
        }
    }

    /**
     * Retrieves the weather data based on the user's location or the default city.
     */
    private fun getDate() {
        if (isInternetConnection()) {
            if (isLocationAvailable()) {
                getLocation()
            } else {
                viewModel.getWeatherData(CITY)
                requireContext().makeToast(getString(R.string.enable_location))
            }
        } else viewModel.setError(getString(R.string.no_internet))
    }

    /**
     * Gets the user's current location and retrieves weather data based on it or handle errors and show toast message.
     */
    private fun getLocation() {
        try {
            LocationServices.getFusedLocationProviderClient(requireContext()).let { client ->
                client.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    CancellationTokenSource().token
                )
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful && task.result != null) {
                            val location = task.result
                            viewModel.getWeatherData("${location.latitude}, ${location.longitude}") //Retrieves the weather data based on the user's location.
                        } else {
                            viewModel.getWeatherData(CITY) //Retrieves the weather data based on the default city.
                            requireContext().makeToast(getString(R.string.error_permission))
                        }
                    }
                    .addOnFailureListener { exception ->
                        requireContext().makeToast(
                            exception.message ?: getString(R.string.error_get_location)
                        )
                    }
            }
        } catch (e: SecurityException) {
            requireContext().makeToast(e.message ?: getString(R.string.error_permission))
        } catch (e: Exception) {
            requireContext().makeToast(getString(R.string.error_with_message, e.message))
        }
    }

    private fun isInternetConnection(): Boolean {
        val cm =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetwork != null
    }

    private fun isLocationAvailable(): Boolean {
        val lm = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isLocationEnabled
    }

    private fun updateCurrentCard(item: CurrentDayModel) {
        binding?.run {
            tvData.text = item.dateTime
            tvCity.text = item.city
            tvCurrentTemp.text = getString(R.string.currentTemp, item.currentTemp)
            tvCondition.text = item.condition
            tvMaxMin.text = getString(R.string.dateHours, item.maxTemp, item.mintTemp)
            Picasso.get().load(SCHEME + item.imageUrl).into(imWeather)
        }
    }

    private fun showHome() {
        binding?.run {
            dayCard.visibility = View.VISIBLE
            tvError.visibility = View.GONE
            tryAgain.visibility = View.GONE
        }
    }

    private fun showError(toastError: String, textError: Int) {
        binding?.run {
            dayCard.visibility = View.GONE
            tvError.visibility = View.VISIBLE
            tryAgain.visibility = View.VISIBLE
            tvError.text = getString(textError)
            requireActivity().makeToast(toastError)
        }
    }

    private fun showDialog(
        title: String,
        message: String,
        listener: (city: String) -> Unit
    ) {
        val cityName = EditText(requireContext())
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setView(cityName)
            .setPositiveButton(getString(R.string.positive_button)) { _, _ ->
                listener(cityName.text.toString())
            }
            .setNegativeButton(getString(R.string.negative_button)) { _, _ -> }
            .create()
            .show()
    }
}