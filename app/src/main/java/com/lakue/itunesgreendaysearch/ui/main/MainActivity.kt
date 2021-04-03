package com.lakue.itunesgreendaysearch.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.lakue.itunesgreendaysearch.R
import com.lakue.itunesgreendaysearch.base.BaseActivity
import com.lakue.itunesgreendaysearch.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    override fun init() {
        binding.apply{
            NavigationUI.setupWithNavController(bottomNavigationView, findNavController(
                R.id.nav_host_fragment))
            bottomNavigationView.itemIconTintList = null
        }
    }
}