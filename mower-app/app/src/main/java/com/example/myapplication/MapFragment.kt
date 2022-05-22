package com.example.myapplication

import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageSwitcher
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                view.findViewById<ImageButton>(R.id.toggleButtonOn).setImageDrawable(toggleon)
                BluetoothControllerActivity().sendCommand(BluetoothControllerActivity().SEND_START_AUTO_MODE)
            }
        }

        val mapSquare = view.findViewById<RelativeLayout>(R.id.mapSquare)
        println("okkk")
        val map = ImageView(context)
        map.setImageResource(R.drawable.onmap)
        val mowerIcon = ImageView(context)
        mowerIcon.setImageResource(R.drawable.mowericon)
        mapSquare.addView(map)
        mapSquare.addView(mowerIcon)

        val startingPoint = ImageView(context)
        startingPoint.setImageResource(R.drawable.startingpoint)
        startingPoint.x = 500.0F
        startingPoint.y = 400.0F
        mapSquare.addView(startingPoint)

        val coordinatesArray = coordinatesMap.getValue(listOfJourneys[0].id.toString())

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
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}