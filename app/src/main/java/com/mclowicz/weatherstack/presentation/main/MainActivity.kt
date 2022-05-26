package com.mclowicz.weatherstack.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.mclowicz.weatherstack.R
import com.mclowicz.weatherstack.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavComponent()
    }

    private fun initNavComponent() {
        setSupportActionBar(binding.toolbar)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.destination_search)
        )
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
            .navController
            .apply {
                setupActionBarWithNavController(this, appBarConfiguration)
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
    }
}