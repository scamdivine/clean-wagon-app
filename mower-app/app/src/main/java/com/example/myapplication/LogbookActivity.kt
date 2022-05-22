package com.example.myapplication

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import java.io.Serializable
import java.net.HttpURLConnection
import java.net.URL

class LogbookActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logbook)
        val returnButton = findViewById<ImageView>(R.id.returnArrow)
        val url = "http://ec2-16-170-167-138.eu-north-1.compute.amazonaws.com/api/v1/events/by-mowerid/2?limit=5"
        AsyncTaskHandleJson().execute(url)

        returnButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun buildLogbook() {
        val eventsArray : ArrayList<Notification> = ArrayList()
        for ((index, event) in listOfEvents.withIndex()) {
            eventsArray.add(Notification(listOfEvents[index].imageID, event.eventType, event.time.slice(0..9), event.objectDesc))
        }

        val logbookLayout = findViewById<LinearLayout>(R.id.logbookLayout)

        var x = 0;
        for ((index, event) in eventsArray.withIndex()){
            val entry = LayoutInflater.from(this).inflate(R.layout.logbook_entry, null, false)
            entry.findViewById<ImageView>(R.id.entryScreenshot).setImageBitmap(imageMap.getValue(listOfEvents[index].imageID))
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
    }

    inner class AsyncTaskHandleJson : AsyncTask<String, String, String>(){
        override fun doInBackground(vararg url: String?): String {
            var event: String
            val connection = URL(url[0]).openConnection() as HttpURLConnection
            try {
                connection.connect()
                event = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
            }finally {
                connection.disconnect()
            }
            return event
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJson(result)
            buildLogbook()
        }

        private fun handleJson(jsonString: String?){
            val jsonArray = JSONArray(jsonString)
            var x=0
            while (x < jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(x)
                listOfEvents.add(Events(
                    jsonObject.getString("coordinate_id"),
                    jsonObject.getString("event_type"),
                    jsonObject.getString("image_id"),
                    jsonObject.getString("object_desc"),
                    jsonObject.getString("time"),
                )
                )
                val url = listOfEvents[x].imageID
                AsyncTaskHandleJsonImage().execute(url)
                x++
            }
        }
    }


    inner class AsyncTaskHandleJsonImage : AsyncTask<String, String, String>(){
        override fun doInBackground(vararg url: String?): String? {
            var byteArray: ByteArray
            val connection = URL("http://ec2-16-170-167-138.eu-north-1.compute.amazonaws.com/api/v1/images/"+url[0]).openConnection() as HttpURLConnection
            try {
                println("TESTSTSTSTSTSTTSTS")
                connection.connect()
                byteArray = connection.inputStream.readBytes()
            }finally {
                connection.disconnect()
            }
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            url[0]?.let { imageMap.put(it, bitmap) }
            return ""
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
        }
    }
}