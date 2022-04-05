package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var loginButton = findViewById<Button>(R.id.loginButton)
        var multipleMower = true

        loginButton.setOnClickListener(){
            val intent = if (multipleMower) Intent(this, SelectionActivity::class.java)
                else Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }




}