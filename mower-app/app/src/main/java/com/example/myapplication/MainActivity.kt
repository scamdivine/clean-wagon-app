@file:Suppress("DEPRECATION")

package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R.id.fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() ,
    BottomNavigationView.OnNavigationItemSelectedListener
{
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bundle = Bundle()
        val navController = findNavController(fragment)
        val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        navController.navigate(R.id.offlineFragment, bundle)
        navView.setOnNavigationItemSelectedListener(this)

        val mowerName = "HUSQVARNA AUTOMOWER® 450X"
        val isActive = true
        val mowerImage = R.drawable.mower_105;

        val statusImageView = findViewById<ImageView>(R.id.statusImage)

        findViewById<TextView>(R.id.mowerName).text = mowerName;
        findViewById<ImageView>(R.id.mowerImage).setImageResource(mowerImage)

        if (isActive)
            statusImageView.setImageResource(R.drawable.active_status)
        else
            statusImageView.setImageResource(R.drawable.inactive_status)

        val returnButton = findViewById<ImageView>(R.id.returnArrow)
        returnButton.setOnClickListener{
            startActivity(Intent(this, SelectionActivity::class.java))
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.MapFragment -> {
                //val bundle = Bundle()
                //bundle.putString(GROUPID, groupId)
                val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
                val navController = findNavController(findViewById(fragment))
                navController.saveState()
                navView.setupWithNavController(navController)
                navController.navigate(R.id.MapFragment, Bundle())
            }

            R.id.ManualDriveFragment -> {
                val bundle = Bundle()
                //bundle.putString(GROUPID, groupId)
                val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
                val navController = findNavController(findViewById(fragment))
                navController.saveState()
                navView.setupWithNavController(navController)
                navController.navigate(R.id.ManualDriveFragment, bundle)
            }

            R.id.SettingsFragment -> {
                val bundle = Bundle()
                //bundle.putString(GROUPID, groupId)
                val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
                val navController = findNavController(findViewById(fragment))
                navController.saveState()
                navView.setupWithNavController(navController)
                navController.navigate(R.id.SettingsFragment, bundle)
            }
        }
        return true
    }
}
