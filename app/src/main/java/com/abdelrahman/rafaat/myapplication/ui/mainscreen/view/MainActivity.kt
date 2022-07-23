package com.abdelrahman.rafaat.myapplication.ui.mainscreen.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import butterknife.BindView
import butterknife.ButterKnife
import com.abdelrahman.rafaat.myapplication.R
import com.abdelrahman.rafaat.myapplication.ui.setting.SettingActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.tab_layout_view)
    lateinit var tabLayout: TabLayout

    @BindView(R.id.view_pager)
    lateinit var viewPager: ViewPager2

    @BindView(R.id.drawer_layout)
    lateinit var drawerLayout: DrawerLayout


    @BindView(R.id.toolBar)
    lateinit var toolBar: Toolbar

    @BindView(R.id.navigation_view)
    lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)


        val toggle =
            ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled
        toggle.drawerArrowDrawable.color = getColor(R.color.white)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        tabLayout.addTab(tabLayout.newTab().setText(R.string.home))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.sport))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.science))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.health))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.business))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.entertainment))

        viewPager.adapter =
            ViewPagerAdapter(this.lifecycle, supportFragmentManager, tabLayout.tabCount)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.getTabAt(position)!!.select()
                Log.i(TAG, "onPageSelected: position-----------> $position")
            }
        })

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab?.position ?: 0
                Log.i(TAG, "onTabSelected: position ------> ${tab?.position}")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.i(TAG, "onTabUnselected: position ------> ${tab!!.position}")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.i(TAG, "onTabReselected: position ------> ${tab!!.position}")
            }

        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.home -> viewPager.currentItem = 0
            R.id.sport -> viewPager.currentItem = 1
            R.id.health -> viewPager.currentItem = 2
            R.id.science -> viewPager.currentItem = 3
            R.id.business -> viewPager.currentItem = 4
            R.id.entertainment -> viewPager.currentItem = 5
            R.id.setting -> startActivity(Intent(this, SettingActivity::class.java))
        }
        drawerLayout.closeDrawer(Gravity.START)
        return true
    }
}