package com.example.weatherforecasts.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.weatherforecasts.R
import com.example.weatherforecasts.databinding.ActivityMainBinding
import com.example.weatherforecasts.navigation.Navigation
import com.example.weatherforecasts.ui.homeScreen.HomeFragment

class MainActivity : AppCompatActivity(), Navigation {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        if (savedInstanceState == null) {
            firstLaunch(HomeFragment())
        }
    }

    override fun firstLaunch(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun startFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun closeFragment() {
        supportFragmentManager.popBackStack()
    }
}