package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class SettingsFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val batteryPercentage = 24;
        val mowerName = "HUSQVARNA AUTOMOWER® 450X"
        val isActive = true
        val mowerImage = R.drawable.mower_105;

        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val logbookButton = view.findViewById<Button>(R.id.logbookButton)
        logbookButton.setOnClickListener{
            startActivity(Intent(context, LogbookActivity::class.java))
        }
        return view
    }
}