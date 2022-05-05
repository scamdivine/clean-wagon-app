package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SelectionActivity : AppCompatActivity() {

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println(listOfEvents)
        setContentView(R.layout.activity_selection)
        val allMowers = arrayOf(1, 2, 3, 4)
        val selectMowerButton = findViewById<Button>(R.id.selectMowerButton)
        val mowerTextView = findViewById<TextView>(R.id.mowerTextView)
        val mowerStatus = findViewById<ImageView>(R.id.MowerStatus)
        val batteryTextView = findViewById<TextView>(R.id.batteryTextView)
        val leftArrow = findViewById<ImageView>(R.id.arrowLeft)
        val rightArrow = findViewById<ImageView>(R.id.arrowRight)
        val addMower = findViewById<ImageView>(R.id.addMower)
        val selectionFragment = SelectionFragment()
        var currentMower = "Husqvarna Automower 105"
        var batteryLvl = 75
        var batteryPresent = batteryLvl.toString() + "% "
        var isActive = true
        var currentMowerCount = 1
        if (currentMowerCount == 1){
            leftArrow.alpha = 0.0f
        }
        if (allMowers.size<2){
            leftArrow.alpha = 0.0f
            rightArrow.alpha = 0.0f
        }
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

        leftArrow.setOnClickListener {
            if (currentMowerCount > 1){
                currentMowerCount -= 1
                rightArrow.alpha = 1.0f
            }
            if (currentMowerCount == 1){
                leftArrow.alpha = 0.0f
            }
            println(currentMowerCount)
        }
        rightArrow.setOnClickListener {
            if (currentMowerCount < allMowers.size){
                currentMowerCount += 1
                leftArrow.alpha = 1.0f
            }
            if (currentMowerCount == allMowers.size) {
                rightArrow.alpha = 0.0f
            }
            println(currentMowerCount)
        }
        selectMowerButton.setOnClickListener(){
            val intent =  Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        addMower.setOnClickListener{
            supportFragmentManager.beginTransaction().add(R.id.container, selectionFragment).addToBackStack("SelectionActivity").commit()
        }
    }
}
