package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class LogbookActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logbook)

        val returnButton = findViewById<ImageView>(R.id.returnArrow)

        val logbookLayout = findViewById<LinearLayout>(R.id.logbookLayout)

        var x = 0;
        while (x != 10){
            val entry = LayoutInflater.from(this).inflate(R.layout.logbook_entry, null, false)
            entry.findViewById<ImageView>(R.id.entryScreenshot).setImageResource(R.drawable.camera_screenshot)
            entry.findViewById<TextView>(R.id.entryMessage).setText("I'm stuck, help me !")
            entry.findViewById<TextView>(R.id.entryDate).setText("05/04/2022 - 17h33")
            entry.findViewById<ImageView>(R.id.entryStatus).setImageResource(R.drawable.inactive_status)
            logbookLayout.addView(entry);
            entry.setOnClickListener {
                val notification = Notification()
                notification.entryScreenshot = R.drawable.camera_screenshot
                notification.entryType = "collision"
                notification.entryDate = "05/04/2022 - 17h33"
                notification.entryStatus = true
                val intent = Intent(this, NotificationActivity::class.java)
                intent.putExtra("data", notification as Serializable)
                startActivity(intent)
            }
            x++
        }

        returnButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}