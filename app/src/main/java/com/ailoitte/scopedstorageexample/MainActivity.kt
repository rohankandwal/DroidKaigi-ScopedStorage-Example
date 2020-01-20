package com.ailoitte.scopedstorageexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ailoitte.scopedstorageexample.ui.MainTabPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vpTabs.adapter = MainTabPagerAdapter(supportFragmentManager)
        vpTabs.offscreenPageLimit = vpTabs.childCount

        tabLayout.setupWithViewPager(vpTabs)
    }
}
