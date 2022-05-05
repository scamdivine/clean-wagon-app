package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable
import java.net.HttpURLConnection
import java.net.URL

class LogbookActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logbook)

        val eventsArray : ArrayList<Notification> = ArrayList()
        for (event in listOfEvents) {
            eventsArray.add(Notification(R.drawable.camera_screenshot, event.eventType, "05/04/2022 - 17h33"))
        }
        val returnButton = findViewById<ImageView>(R.id.returnArrow)

        val logbookLayout = findViewById<LinearLayout>(R.id.logbookLayout)

        var x = 0;
        for ((index, event) in eventsArray.withIndex()){
            val entry = LayoutInflater.from(this).inflate(R.layout.logbook_entry, null, false)
            entry.findViewById<ImageView>(R.id.entryScreenshot).setImageResource(event.screenshot)
            entry.findViewById<TextView>(R.id.entryDate).setText(event.date)
            if (event.type == "Obstacle") {
                entry.findViewById<TextView>(R.id.entryMessage).setText("I'm stuck, help me !")
                entry.findViewById<ImageView>(R.id.entryStatus).setImageResource(R.drawable.inactive_status)
            } else {
                entry.findViewById<TextView>(R.id.entryMessage).setText("I bumped into something !")
                entry.findViewById<ImageView>(R.id.entryStatus).setImageResource(R.drawable.active_status)
            }
            logbookLayout.addView(entry);
            entry.setOnClickListener {
                val intent = Intent(this, NotificationActivity::class.java)
                intent.putExtra("data", event as Serializable)
                startActivity(intent)
            }
        }

        returnButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}