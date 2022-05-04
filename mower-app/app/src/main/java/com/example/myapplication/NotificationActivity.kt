package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class Notification(val entryScreenshot: Int, val entryType: String?, val entryDate: String?) : Serializable {
    var screenshot = entryScreenshot
    var type = entryType
    var date = entryDate
}

class NotificationActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val returnButton = findViewById<ImageView>(R.id.returnArrow)

        returnButton.setOnClickListener{
            startActivity(Intent(this, LogbookActivity::class.java))
        }

        val notification = intent.extras?.get("data") as Notification
        if (notification.type == "Obstacle") {
            findViewById<TextView>(R.id.notificationTitle).text = "I'm stuck, help me !"
            findViewById<TextView>(R.id.notificationDescription).text = "Your automower met an obstacle and is now stuck."
            findViewById<ImageView>(R.id.notificationStatus).setImageResource(R.drawable.inactive_status)
        } else {
            findViewById<TextView>(R.id.notificationTitle).text = "I bumped into something !"
            findViewById<TextView>(R.id.notificationDescription).text = "Your automower met an obstacle and is now stuck."
            findViewById<ImageView>(R.id.notificationStatus).setImageResource(R.drawable.active_status)
        }

        findViewById<TextView>(R.id.notificationDate).text = notification.date
        findViewById<ImageView>(R.id.notificationImage).setImageResource(notification.screenshot)
    }
}