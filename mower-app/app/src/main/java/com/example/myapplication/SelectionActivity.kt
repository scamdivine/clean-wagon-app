package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SelectionActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)

        var selectMowerButton = findViewById<Button>(R.id.selectMowerButton)

        selectMowerButton.setOnClickListener(){
            val intent =  Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}