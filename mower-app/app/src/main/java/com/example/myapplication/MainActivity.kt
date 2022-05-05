@file:Suppress("DEPRECATION")

package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragment)
        navView.setupWithNavController(navController)
        val returnButton = findViewById<ImageView>(R.id.returnArrow)
        returnButton.setOnClickListener{
            startActivity(Intent(this, SelectionActivity::class.java))
        }
    }
}
