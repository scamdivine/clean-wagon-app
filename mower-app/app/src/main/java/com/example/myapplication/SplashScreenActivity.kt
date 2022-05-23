package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import kotlinx.coroutines.*
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import android.util.Base64
import androidx.core.graphics.createBitmap
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

var listOfEvents = ArrayList<Events>()
var listOfJourneys = ArrayList<Journey>()
var imageMap = HashMap<String, Bitmap>()
var coordinatesMap = HashMap<String, ArrayList<Coordinates>>()
var isStartUp = true

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val url = "http://ec2-16-170-167-138.eu-north-1.compute.amazonaws.com/api/v1/events/by-mowerid/2?limit=5"
        val journeysURL = "http://ec2-16-170-167-138.eu-north-1.compute.amazonaws.com/api/v1/journeys/by-mowerid/2"
        AsyncTaskHandleJsonJourneys().execute(journeysURL)
        AsyncTaskHandleJson().execute(url)
    }

    inner class AsyncTaskHandleJson : AsyncTask<String,String,String>(){
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
        }

        private fun handleJson(jsonString: String?){
            listOfEvents.clear()
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
            Handler().postDelayed({
                val intent =  Intent(baseContext, SelectionActivity::class.java)
                startActivity(intent)
                                  },7000)

        }
    }


    inner class AsyncTaskHandleJsonImage : AsyncTask<String,String,String>(){
        override fun doInBackground(vararg url: String?): String? {
            var byteArray: ByteArray
            val connection = URL("http://ec2-16-170-167-138.eu-north-1.compute.amazonaws.com/api/v1/images/"+url[0]).openConnection() as HttpURLConnection
            try {
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

    inner class AsyncTaskHandleJsonJourneys : AsyncTask<String, String, String>(){
        override fun doInBackground(vararg journeysURL: String?): String? {
            var event: String
            val connection = URL(journeysURL[0]).openConnection() as HttpURLConnection
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
        }

        private fun handleJson(jsonString: String?){
            if (!listOfJourneys.isEmpty()){
                isStartUp = false
            }
            listOfJourneys.clear()
            val jsonArray = JSONArray(jsonString)
            var x=0
            while (x < jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(x)
                listOfJourneys.add(Journey(
                        jsonObject.getInt("id"),
                        jsonObject.getInt("mower_id"),
                        jsonObject.getString("start_time"),
                        jsonObject.getString("end_time"),
                    )
                )
                if (isStartUp){
                    val url = listOfJourneys[listOfJourneys.size-1].id.toString()
                    AsyncTaskHandleJsonCoordinates().execute(url)
                }
                x++

            }
            if (!isStartUp){
                val url = listOfJourneys[listOfJourneys.size-1].id.toString()
                AsyncTaskHandleJsonCoordinates().execute(url)
            }
        }
    }

    inner class AsyncTaskHandleJsonCoordinates : AsyncTask<String,String,String>(){
        override fun doInBackground(vararg url: String?): String? {
            var event: String
            val connection = URL("http://ec2-16-170-167-138.eu-north-1.compute.amazonaws.com/api/v1/journeys/"+url[0]).openConnection() as HttpURLConnection
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
        }

        private fun handleJson(jsonString: String?){
            val json = JSONObject(jsonString)
            val coordinatesArray = json.getJSONArray("coordinates")
            var newSet = ArrayList<Coordinates>()
            for (i in 0 until coordinatesArray.length()) {
                var coordinate = coordinatesArray.getJSONObject(i)
                newSet.add(Coordinates(
                        coordinate.getInt("id"),
                        coordinate.getInt("x"),
                        coordinate.getInt("y"),
                        coordinate.getInt("is_event"),
                    )
                )
            }
            json.getJSONObject("journey").getInt("id").toString()?.let { coordinatesMap.put(it, newSet) }
        }
    }
}