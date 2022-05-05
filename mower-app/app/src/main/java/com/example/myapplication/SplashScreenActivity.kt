package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
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
import androidx.core.graphics.createBitmap
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

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
            var imageFromBackend: JSONObject
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
            val url = "http://ec2-16-170-167-138.eu-north-1.compute.amazonaws.com/api/v1/images/ee3ed9270de1924b826ad3214adcca45"
            val events = AsyncTaskHandleJsonImage().execute(url)
            //val intent =  Intent(baseContext, SelectionActivity::class.java)
            //println("start activity")
            //startActivity(intent)

        }
    }

    inner class AsyncTaskHandleJsonImage : AsyncTask<String,String,String>(){
        override fun doInBackground(vararg url: String?): String {
            println("calling backend")
            var text: ByteArray
            val connection = URL(url[0]).openConnection() as HttpURLConnection
            try {
                connection.connect()
                text = connection.inputStream.readBytes()
            }finally {
                connection.disconnect()
            }
            Log.d("text", text.toString())
            print(text)
            val bitmap = BitmapFactory.decodeByteArray(text, 0, text.size)
            Log.d("bitmap", bitmap.toString())
            val testImage = bitmap
            val intent =  Intent(baseContext, SelectionActivity::class.java)
            intent.putExtra("image", text)
            println("start activity")
            startActivity(intent)
            print(bitmap)
            return "bitmap"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJsonImage(result)
        }

        private fun handleJsonImage(jsonString: String?){
            //val intent =  Intent(baseContext, SelectionActivity::class.java)
            //intent.putExtra("image", jsonString)
            //println("start activity")
            //startActivity(intent)
        }
    }
}