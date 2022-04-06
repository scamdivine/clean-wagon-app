package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)

        var selectMowerButton = findViewById<Button>(R.id.selectMowerButton)
        var mowerTextView = findViewById<TextView>(R.id.mowerTextView)
        var statusTextView = findViewById<TextView>(R.id.statusTextView)
        var currentMower = "Husqvarna Automower 105"
        var isActive = false
        statusTextView.setBackgroundResource(R.drawable.active_background);


        val drawable = statusTextView.getBackground()
        if (isActive) {
            drawable.colorFilter(Color.RED)
        } else {
            drawable.setColor(Color.BLUE)
        }


        mowerTextView.text = currentMower
        selectMowerButton.setOnClickListener(){
            val intent =  Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
