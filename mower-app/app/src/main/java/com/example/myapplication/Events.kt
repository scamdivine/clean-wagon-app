package com.example.myapplication

import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL


data class Events(var temp: String) {

    open fun getAllEvents(): AsyncTask<String, String, String>? {
        val url = "http://ec2-16-170-167-138.eu-north-1.compute.amazonaws.com/api/v1/events/by-mowerid/2?limit=1"
        val events = AsyncTaskHandleJson().execute(url)

        return events

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

    }

    private fun handleJson(jsonString: String?){
        val jsonArray = JSONArray(jsonString)
        println(jsonArray)
    }
}