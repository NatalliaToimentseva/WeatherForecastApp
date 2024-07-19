package com.example.weatherforecasts.navigation

import androidx.fragment.app.Fragment

interface Navigation {

    fun firstLaunch(fragment: Fragment)

    fun startFragment(fragment: Fragment)

    fun closeFragment()
}

fun Fragment.navigator(): Navigation {
    return requireActivity() as Navigation
}