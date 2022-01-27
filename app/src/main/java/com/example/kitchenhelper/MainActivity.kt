package com.example.kitchenhelper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kitchenhelper.core.setupWithNavController
import com.example.kitchenhelper.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        bottomNav = findViewById(R.id.main_bottom_bar)
    }

    override fun onStart() {
        super.onStart()

        setBottomItemSelectedListener()
    }

    private fun setBottomItemSelectedListener() {
        bottomNav.setupWithNavController(
            navGraphIds = listOf(
                R.navigation.kitchen_helper_nav,
                R.navigation.video_navigation
            ),
            fragmentManager = supportFragmentManager,
            containerId = R.id.fragments_container,
            intent = intent
        )
    }

}