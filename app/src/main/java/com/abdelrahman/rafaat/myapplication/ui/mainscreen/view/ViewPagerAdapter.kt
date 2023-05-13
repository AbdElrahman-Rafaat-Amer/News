package com.abdelrahman.rafaat.myapplication.ui.mainscreen.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.abdelrahman.rafaat.myapplication.ui.fragments.*

class ViewPagerAdapter(
    lifecycle: Lifecycle,
    fragmentManager: FragmentManager,
    private var tabsNumber: Int
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = tabsNumber

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> SportFragment()
            2 -> HealthFragment()
            3 -> ScienceFragment()
            4 -> BusinessFragment()
            5 -> EntertainmentFragment()
            else -> HomeFragment()
        }
    }

}