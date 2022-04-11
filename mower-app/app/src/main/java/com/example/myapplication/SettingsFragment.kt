package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class SettingsFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstantState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        view.findViewById<TextView>(R.id.mowerName).text = "HUSQVARNA AUTOMOWER® 450X";
        view.findViewById<ImageView>(R.id.mowerImage).setImageResource(R.drawable.facebook_logo)
        view.findViewById<ImageView>(R.id.batteryImage).setImageResource(R.drawable.facebook_logo)
        view.findViewById<ImageView>(R.id.statusImage).setImageResource(R.drawable.facebook_logo)
        view.findViewById<TextView>(R.id.batteryPercentage).text = "82%"
        return view
    }
}