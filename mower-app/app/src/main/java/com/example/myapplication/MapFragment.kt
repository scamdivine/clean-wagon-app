package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment


class MapFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val toggleon =  resources.getDrawable(R.drawable.toggleon)
        val toggleoff = resources.getDrawable(R.drawable.toggleoff)
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        view.findViewById<ImageButton>(R.id.toggleButtonOn).setOnClickListener{
            if(view.findViewById<ImageButton>(R.id.toggleButtonOn).drawable.equals(toggleon))
                view.findViewById<ImageButton>(R.id.toggleButtonOn).setImageDrawable(toggleoff)
            else
                view.findViewById<ImageButton>(R.id.toggleButtonOn).setImageDrawable(toggleon)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}