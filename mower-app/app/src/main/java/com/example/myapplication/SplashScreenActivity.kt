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
var imageMap = HashMap<String, Bitmap>()

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val url = "http://ec2-16-170-167-138.eu-north-1.compute.amazonaws.com/api/v1/events/by-mowerid/2?limit=5"
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
            val jsonArray = JSONArray(jsonString)
            var x=0
            while (x < jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(x)
                listOfEvents.add(Events(
                    jsonObject.getString("coordinate_id"),
                    jsonObject.getString("event_type"),
                    jsonObject.getString("image_id"),
                    jsonObject.getString("object_desc"),
                )
                )
                val url = listOfEvents[x].imageID
                AsyncTaskHandleJsonImage().execute(url)
                x++
            }
            val intent =  Intent(baseContext, SelectionActivity::class.java)
            startActivity(intent)
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
}