package com.example.myapplication

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        var events: AsyncTask<String, String, String>?
        var test = false
        GlobalScope.launch {
            events = async { networkCall() }.await()
            Log.d("test", test.toString())
            test = true
            Log.d("eveents", events.toString())

        }

        when (test == true){
            test == true -> {
                val intent =  Intent(this, SelectionActivity::class.java)
                println("start activity")
                startActivity(intent)
            }
        }


    }

    suspend fun networkCall() : AsyncTask<String, String, String>? {
        val events = Events("yeye").getAllEvents()

        return events
    }
}