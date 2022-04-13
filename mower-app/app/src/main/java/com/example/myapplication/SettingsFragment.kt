package com.example.myapplication

import android.graphics.Color
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
        val batteryPercentage = 24;
        val mowerName = "HUSQVARNA AUTOMOWER® 450X"
        val isActive = true
        val mowerImage = R.drawable.mower_105;

        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val batteryImageView = view.findViewById<ImageView>(R.id.batteryImage)
        val batteryTextView = view.findViewById<TextView>(R.id.batteryPercentage)
        val statusImageView = view.findViewById<ImageView>(R.id.statusImage)

        view.findViewById<TextView>(R.id.mowerName).text = "HUSQVARNA AUTOMOWER® 450X";
        view.findViewById<ImageView>(R.id.mowerImage).setImageResource(mowerImage)
        view.findViewById<TextView>(R.id.batteryPercentage).text = batteryPercentage.toString() + "%";

        if (isActive)
            statusImageView.setImageResource(R.drawable.active_status)
        else
            statusImageView.setImageResource(R.drawable.inactive_status)

        if (batteryPercentage >= 50) {
            batteryImageView.setImageResource(R.drawable.battery_full)
            batteryTextView.setTextColor(Color.parseColor("#95E05B"))
        } else if (batteryPercentage < 50 && batteryPercentage >= 20) {
            batteryImageView.setImageResource(R.drawable.battery_half)
            batteryTextView.setTextColor(Color.parseColor("#F2C759"))
        } else {
            batteryImageView.setImageResource(R.drawable.battery_low)
            batteryTextView.setTextColor(Color.parseColor("#F24E1E"))
        }
        return view
    }
}