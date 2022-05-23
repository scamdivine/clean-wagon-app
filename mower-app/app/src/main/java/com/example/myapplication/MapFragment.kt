package com.example.myapplication

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment

var toggledOn = false;
var scaleX = 1
var scaleY = 2

class Coordinate(val x_axis: Float, val y_axis: Float, val hasEvent: Boolean)  {
    var x = x_axis
    var y = y_axis
    var event = hasEvent
}

class MapFragment: Fragment() {
    lateinit var mainHandler: Handler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainHandler = Handler(Looper.getMainLooper())
        val toggleon =  resources.getDrawable(R.drawable.toggleon)
        val toggleoff = resources.getDrawable(R.drawable.toggleoff)
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        if(toggledOn == false)
        {
            view.findViewById<ImageButton>(R.id.toggleButtonOn).setImageDrawable(toggleoff)
        }
        else {
            view.findViewById<ImageButton>(R.id.toggleButtonOn).setImageDrawable(toggleon)
        }

        view.findViewById<ImageButton>(R.id.toggleButtonOn).setOnClickListener{
            if(view.findViewById<ImageButton>(R.id.toggleButtonOn).drawable.equals(toggleon)) {
                toggledOn = false;
                view.findViewById<ImageButton>(R.id.toggleButtonOn).setImageDrawable(toggleoff)
                BluetoothControllerActivity().sendCommand(BluetoothControllerActivity().SEND_STOP_AUTO_MODE)
            }
            else {
                toggledOn = true;
                Handler().postDelayed({
                    val journeysURL = "http://ec2-16-170-167-138.eu-north-1.compute.amazonaws.com/api/v1/journeys/by-mowerid/2"
                    SplashScreenActivity().AsyncTaskHandleJsonJourneys().execute(journeysURL)
                }, 1000)
                view.findViewById<ImageButton>(R.id.toggleButtonOn).setImageDrawable(toggleon)
                BluetoothControllerActivity().sendCommand(BluetoothControllerActivity().SEND_START_AUTO_MODE)
            }
        }

        val mapSquare = view.findViewById<RelativeLayout>(R.id.mapSquare)
        val startingPoint = ImageView(context)
        startingPoint.setImageResource(R.drawable.startingpoint)
        println("height = " + startingPoint.getDrawable().getIntrinsicWidth())
        startingPoint.x = 500.0F
        startingPoint.y = 400.0F
        mapSquare.addView(startingPoint)
        println(listOfJourneys)
        return view
    }

    private val updateMapTask = object : Runnable {
        @SuppressLint("ResourceAsColor")
        override fun run() {
            if (toggledOn){
                backendRequest()
            }
            val sizeOfJurneyList= listOfJourneys.size
            val mapSquare = view?.findViewById<RelativeLayout>(R.id.mapSquare)
            var newMap = ImageView(context)
            newMap.setLayoutParams(
                LinearLayout.LayoutParams(1500, 800)
            )
            newMap.setImageResource(R.drawable.map_background)
            mapSquare?.addView(newMap)
            val startingPoint = ImageView(context)
            mapSquare?.setBackgroundResource(R.drawable.map)
            startingPoint.setImageResource(R.drawable.startingpoint)
            println("height = " + startingPoint.getDrawable().getIntrinsicWidth())
            startingPoint.x = 500.0F
            startingPoint.y = 400.0F
            mapSquare?.addView(startingPoint)
            val mowerIcon = view?.findViewById<ImageView>(R.id.mowerIcon)
            val coordinatesArray = coordinatesMap.getValue(listOfJourneys[sizeOfJurneyList-1].id.toString())
            println(coordinatesArray)
            Log.d("testar vi", mapSquare!!.measuredWidth.toString())
            var skipNext = false
            for (coordinate in coordinatesArray) {
                if (skipNext){
                    skipNext = false
                } else {
                    if (coordinate == coordinatesArray.last()) {
                        mowerIcon!!.x = (startingPoint.x + (coordinatesArray.last().x * 10) - 10)
                        mowerIcon.y = (startingPoint.y - (coordinatesArray.last().y * 10) - 10)
                    } else {
                        var newPoint = ImageView(context)
                        if (coordinate.isEvent == 0)
                            newPoint.setImageResource(R.drawable.coordinate)
                        else {
                            newPoint.setImageResource(R.drawable.eventmap)
                        }
                        if (startingPoint.x + (coordinate.x * 2 / scaleX) > mapSquare!!.measuredWidth)
                            scaleX += 1
                        if (startingPoint.y - (coordinate.x * 2 / scaleY) > mapSquare!!.measuredHeight)
                            scaleY += 1
                        newPoint.x = (startingPoint.x + (coordinate.x * 2 / scaleX))
                        newPoint.y = (startingPoint.y - (coordinate.y * 2/ scaleY))
                        mapSquare?.addView(newPoint)
                    }
                }
            }
            mainHandler.postDelayed(this, 1000)
        }
    }
    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(updateMapTask)
    }
    override fun onResume() {
        super.onResume()
        mainHandler.post(updateMapTask)
    }
    fun backendRequest(){
        listOfJourneys.size
        SplashScreenActivity().AsyncTaskHandleJsonCoordinates().execute(listOfJourneys[listOfJourneys.size-1].id.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}