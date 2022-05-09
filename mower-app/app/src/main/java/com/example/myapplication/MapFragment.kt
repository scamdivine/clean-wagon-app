package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment

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
        val coordinates = arrayOf(
            Coordinate(50.0F, 50.0F, false),
            Coordinate(100.0F, 100.0F, false),
            Coordinate(200.0F, 200.0F, true),
            Coordinate(200.0F, 300.0F, false),
        )

        val mapSquare = view.findViewById<RelativeLayout>(R.id.mapSquare)
        val startingPoint = ImageView(context)
        startingPoint.setImageResource(R.drawable.coordinate)
        startingPoint.x = 100.0F
        startingPoint.y = 700.0F
        mapSquare.addView(startingPoint)
        for (coordinate in coordinates) {
            var newPoint = ImageView(context)
            if (!coordinate.event)
                newPoint.setImageResource(R.drawable.coordinate)
            else
                newPoint.setImageResource(R.drawable.eventmap)
            newPoint.x = (100.0F + coordinate.x)
            newPoint.y = (700.0F - coordinate.y)
            mapSquare.addView(newPoint)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}