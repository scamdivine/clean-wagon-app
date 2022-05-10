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

class MapFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        view.findViewById<ImageButton>(R.id.toggleButtonOn).setOnClickListener{
            findNavController(view).navigate(R.id.action_MapFragment_to_OfflineFragment)
        }

        val mapSquare = view.findViewById<RelativeLayout>(R.id.mapSquare)
        val startingPoint = ImageView(context)
        startingPoint.setImageResource(R.drawable.startingpoint)
        println("height = " + startingPoint.getDrawable().getIntrinsicWidth())
        startingPoint.x = 500.0F
        startingPoint.y = 400.0F
        mapSquare.addView(startingPoint)
        println(listOfJourneys)
        val mowerIcon = view.findViewById<ImageView>(R.id.mowerIcon)
        val coordinatesArray = coordinatesMap.getValue(listOfJourneys[0].id.toString())
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
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}