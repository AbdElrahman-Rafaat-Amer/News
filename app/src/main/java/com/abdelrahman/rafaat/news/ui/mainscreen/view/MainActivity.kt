package com.abdelrahman.rafaat.news.ui.mainscreen.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.viewpager2.widget.ViewPager2
import com.abdelrahman.rafaat.news.R
import com.abdelrahman.rafaat.news.databinding.ActivityMainBinding
import com.abdelrahman.rafaat.news.ui.setting.SettingActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navigationView.setNavigationItemSelectedListener(this)
        initDrawerLayout()
        initViewPager()
        initTabLayout()

    }

    private fun initDrawerLayout() {
        val toggle =
            ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolBar, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled
        toggle.drawerArrowDrawable.color = getColor(R.color.white)
        toggle.syncState()
    }

    private fun initViewPager() {
        binding.viewPager.adapter =
            ViewPagerAdapter(this.lifecycle, supportFragmentManager, binding.tabLayoutView.tabCount)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayoutView.getTabAt(position)!!.select()
                Log.i(TAG, "onPageSelected: position-----------> $position")
            }
        })
    }

    private fun initTabLayout() {
        binding.tabLayoutView.addTab(binding.tabLayoutView.newTab().setText(R.string.home))
        binding.tabLayoutView.addTab(binding.tabLayoutView.newTab().setText(R.string.sport))
        binding.tabLayoutView.addTab(binding.tabLayoutView.newTab().setText(R.string.science))
        binding.tabLayoutView.addTab(binding.tabLayoutView.newTab().setText(R.string.health))
        binding.tabLayoutView.addTab(binding.tabLayoutView.newTab().setText(R.string.business))
        binding.tabLayoutView.addTab(binding.tabLayoutView.newTab().setText(R.string.entertainment))

        binding.tabLayoutView.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager.currentItem = tab?.position ?: 0
                Log.i(TAG, "onTabSelected: position ------> ${tab?.position}")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> binding.viewPager.currentItem = 0
            R.id.sport -> binding.viewPager.currentItem = 1
            R.id.health -> binding.viewPager.currentItem = 2
            R.id.science -> binding.viewPager.currentItem = 3
            R.id.business -> binding.viewPager.currentItem = 4
            R.id.entertainment -> binding.viewPager.currentItem = 5
            R.id.setting -> startActivity(Intent(this, SettingActivity::class.java))
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}