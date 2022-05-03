package com.example.myapplication

import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController


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
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        view.findViewById<ImageButton>(R.id.toggleButtonOn).setOnClickListener{
            findNavController(view).navigate(R.id.action_MapFragment_to_OfflineFragment)
        }
        val coordinates = arrayOf(
            Coordinate(50.0F, 50.0F, false),
            Coordinate(100.0F, 100.0F, false),
            Coordinate(200.0F, 200.0F, true),
            Coordinate(200.0F, 300.0F, false),
        )

        val mapSquare = view.findViewById<RelativeLayout>(R.id.mapSquare)
        val startingPoint = ImageView(context)
        startingPoint.setImageResource(R.drawable.startingpoint)
        println("height = " + startingPoint.getDrawable().getIntrinsicWidth())
        startingPoint.x = 500.0F
        startingPoint.y = 400.0F
        mapSquare.addView(startingPoint)
        val mowerIcon = view.findViewById<ImageView>(R.id.mowerIcon)
        for (coordinate in coordinates) {
            if (coordinate == coordinates.last()) {
                mowerIcon.x = (startingPoint.x + coordinates.last().x - 10)
                mowerIcon.y = (startingPoint.y - coordinates.last().y - 10)
            } else {
                var newPoint = ImageView(context)
                if (!coordinate.event)
                    newPoint.setImageResource(R.drawable.coordinate)
                else
                    newPoint.setImageResource(R.drawable.eventmap)
                newPoint.x = (startingPoint.x + coordinate.x)
                newPoint.y = (startingPoint.y - coordinate.y)
                mapSquare.addView(newPoint)
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}