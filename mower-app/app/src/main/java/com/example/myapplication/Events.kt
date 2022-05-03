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


data class Events(val coordinateID: String = "", /*val journeyID: String = "",*/
                  val eventType: String = "", val imageID: String = ""
                  , val objectDesc: String = "") {
}