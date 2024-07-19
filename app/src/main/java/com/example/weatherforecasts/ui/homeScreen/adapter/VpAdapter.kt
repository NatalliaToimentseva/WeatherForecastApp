package com.example.weatherforecasts.ui.homeScreen.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class VpAdapter(fragment: Fragment, private val list: List<Fragment>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}