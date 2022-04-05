@file:Suppress("DEPRECATION")

package com.example.myapplication

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() ,
    BottomNavigationView.OnNavigationItemSelectedListener
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bundle = Bundle()
        val navController = findNavController(findViewById(R.id.fragment))
        navController.navigate(R.id.MapFragment, bundle)
        val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        navView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.MapFragment -> {
                val bundle = Bundle()
                //bundle.putString(GROUPID, groupId)
                val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
                val navController = Navigation.findNavController(findViewById(R.id.fragment))
                navController.saveState()
                navView.setupWithNavController(navController)
                navController.navigate(R.id.MapFragment, bundle)
            }
            R.id.ManualDriveFragment -> {
                val bundle = Bundle()
                //bundle.putString(GROUPID, groupId)
                val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
                val navController = findNavController(findViewById(R.id.fragment))
                navView.setupWithNavController(navController)
                navController.navigate(R.id.ManualDriveFragment, bundle)
            }

            R.id.SettingsFragment -> {
                val bundle = Bundle()
                //bundle.putString(GROUPID, groupId)
                val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
                val navController = Navigation.findNavController(findViewById(R.id.fragment))
                navController.saveState()
                navView.setupWithNavController(navController)
                navController.navigate(R.id.SettingsFragment, bundle)
            }
        }
        return true
    }
}
