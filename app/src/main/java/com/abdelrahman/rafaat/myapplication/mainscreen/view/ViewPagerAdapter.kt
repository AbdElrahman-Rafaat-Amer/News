package com.abdelrahman.rafaat.myapplication.mainscreen.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.abdelrahman.rafaat.myapplication.business.BusinessFragment
import com.abdelrahman.rafaat.myapplication.entertainment.EntertainmentFragment
import com.abdelrahman.rafaat.myapplication.health.HealthFragment
import com.abdelrahman.rafaat.myapplication.home.HomeFragment
import com.abdelrahman.rafaat.myapplication.science.ScienceFragment
import com.abdelrahman.rafaat.myapplication.sport.SportFragment

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