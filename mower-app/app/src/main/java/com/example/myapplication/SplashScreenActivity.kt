package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.Image
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import kotlinx.coroutines.*
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import android.util.Base64

var listOfEvents = ArrayList<Events>()
var eventImage = String

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        var logoImage = findViewById<ImageView>(R.id.logo)
        val url = "http://ec2-16-170-167-138.eu-north-1.compute.amazonaws.com/api/v1/events/by-mowerid/2?limit=5"
        val events = AsyncTaskHandleJson().execute(url)
    }

    inner class AsyncTaskHandleJson : AsyncTask<String,String,String>(){
        override fun doInBackground(vararg url: String?): String {
            println("calling backend")
            var text: String
            val connection = URL(url[0]).openConnection() as HttpURLConnection
            try {
                connection.connect()
                text = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
            }finally {
                connection.disconnect()
            }
            return text
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJson(result)
        }

        private fun handleJson(jsonString: String?){
            println(jsonString)
            val jsonArray = JSONArray(jsonString)
            var x=0
            while (x < jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(x)
                listOfEvents.add(Events(
                    jsonObject.getString("coordinate_id"),
                    //jsonObject.getString("journey_id"),
                    jsonObject.getString("event_type"),
                    jsonObject.getString("image_id"),
                    jsonObject.getString("object_desc"),
                    )
                )
                x++
            }
            val url = "http://ec2-16-170-167-138.eu-north-1.compute.amazonaws.com/api/v1/images/b41edf34e9ac5661ab06dfcaacac699d"
            val events = AsyncTaskHandleJsonImage().execute(url)
            val intent =  Intent(baseContext, SelectionActivity::class.java)
        //    println("start activity")
        //    startActivity(intent)
        }
    }

    inner class AsyncTaskHandleJsonImage : AsyncTask<String,String,String>(){
        override fun doInBackground(vararg url: String?): String {
            println("calling backend")
            var text: String
            val connection = URL(url[0]).openConnection() as HttpURLConnection
            try {
                connection.connect()
                text = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
            }finally {
                connection.disconnect()
            }
            return text
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJson(result)
        }

        private fun handleJson(jsonString: String?){
            println(jsonString)

            //val jsonArray = JSONArray(jsonString)

            //println("yeye"+jsonArray[0])
            val intent =  Intent(baseContext, SelectionActivity::class.java)
            intent.putExtra("image", jsonString)
            //println("start activity")
            startActivity(intent)
        }
    }
}