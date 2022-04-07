package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SelectionActivity : AppCompatActivity() {

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)

        var selectMowerButton = findViewById<Button>(R.id.selectMowerButton)
        var mowerTextView = findViewById<TextView>(R.id.mowerTextView)
        var mowerStatus = findViewById<ImageView>(R.id.MowerStatus)
        var batteryTextView = findViewById<TextView>(R.id.batteryTextView)
        var currentMower = "Husqvarna Automower 105"
        var batteryLvl = 75
        var batteryPresent = batteryLvl.toString() + "% "
        var isActive = true
        if(isActive){
            mowerStatus.setImageResource(R.drawable.active_status)
        } else {
            mowerStatus.setImageResource(R.drawable.inactive_status)
        }

        batteryTextView.text = batteryPresent
        mowerTextView.text = currentMower
        if (batteryLvl>50){
            batteryTextView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.battery_full,0)
            batteryTextView.setTextColor(Color.parseColor("#95E05B"))
        } else if (batteryLvl>20){
            batteryTextView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.battery_half,0)
            batteryTextView.setTextColor(Color.parseColor("#F2C759"))
        } else {
            batteryTextView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.battery_low,0)
            batteryTextView.setTextColor(Color.parseColor("#F24E1E"))
        }
        selectMowerButton.setOnClickListener(){
            val intent =  Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
