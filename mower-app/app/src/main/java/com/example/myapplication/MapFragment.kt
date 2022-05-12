package com.example.myapplication

import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController

var toggledOn = false;

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
   /*     val mowerIcon = view.findViewById<ImageView>(R.id.mowerIcon)
        val coordinatesArray = coordinatesMap.getValue(listOfJourneys[listOfJourneys.size-1].id.toString())
        println(coordinatesArray)
        for (coordinate in coordinatesArray) {
            if (coordinate == coordinatesArray.last()) {
                mowerIcon.x = (startingPoint.x + (coordinatesArray.last().x * 10) - 10)
                mowerIcon.y = (startingPoint.y - (coordinatesArray.last().y * 10) - 10)
            } else {
                var newPoint = ImageView(context)
                if (coordinate.isEvent == 0)
                    newPoint.setImageResource(R.drawable.coordinate)
                else
                    newPoint.setImageResource(R.drawable.eventmap)
                newPoint.x = (startingPoint.x + (coordinate.x * 10))
                newPoint.y = (startingPoint.y - (coordinate.y * 10))
                mapSquare.addView(newPoint)
            }
        }*/
        return view
    }

    private val updateMapTask = object : Runnable {
        override fun run() {
            if (toggledOn){
                backendRequest()
            }

            val mapSquare = view?.findViewById<RelativeLayout>(R.id.mapSquare)
            val startingPoint = ImageView(context)
            startingPoint.setImageResource(R.drawable.startingpoint)
            println("height = " + startingPoint.getDrawable().getIntrinsicWidth())
            startingPoint.x = 500.0F
            startingPoint.y = 400.0F
            mapSquare?.addView(startingPoint)
            val mowerIcon = view?.findViewById<ImageView>(R.id.mowerIcon)
            val coordinatesArray = coordinatesMap.getValue(listOfJourneys[listOfJourneys.size-1].id.toString())
            println(coordinatesArray)
            for (coordinate in coordinatesArray) {
                if (coordinate == coordinatesArray.last()) {
                    mowerIcon!!.x = (startingPoint.x + (coordinatesArray.last().x * 10) - 10)
                    mowerIcon.y = (startingPoint.y - (coordinatesArray.last().y * 10) - 10)
                } else {
                    var newPoint = ImageView(context)
                    if (coordinate.isEvent == 0)
                        newPoint.setImageResource(R.drawable.coordinate)
                    else
                        newPoint.setImageResource(R.drawable.eventmap)
                    newPoint.x = (startingPoint.x + (coordinate.x ))
                    newPoint.y = (startingPoint.y - (coordinate.y))
                    mapSquare?.addView(newPoint)
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