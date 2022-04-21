package com.example.myapplication

import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class backendCommunication() {
    val jsonArray = JSONArray()

    fun getRequest(context: Context){
        lateinit var text : JSONObject
        var amountOfObjects = 0
        var goThrough = 0
        val queue = Volley.newRequestQueue(context)
        val url = "http://ec2-16-170-167-138.eu-north-1.compute.amazonaws.com/api/v1/events/50"
        val objectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                // Display the first 500 characters of the response string.
                amountOfObjects = response.length()
                text = response

            },
            { error ->
                Toast.makeText(context, "Error!",
                    Toast.LENGTH_SHORT).show() })

        queue.add(objectRequest)
    }

    fun sendRequest(){

    }
}