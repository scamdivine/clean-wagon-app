package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment


class MapFragment: Fragment() {
    override fun onCreateView(
        @NonNull inflater: LayoutInflater, @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        //var toggleImage = inflater.context
        inflater.inflate(R.layout.fragment_map, container, false).findViewById<ImageView>(R.id.toggleButton).setOnClickListener{
            var toggleValue = 0;
            if(toggleValue == 0){
                inflater.inflate(R.layout.fragment_map, container, false).findViewById<ImageView>(R.id.toggleButton).setImageResource(R.drawable.toggleon)
                toggleValue = 1;
            }
            else {
                inflater.inflate(R.layout.fragment_map, container, false).findViewById<ImageView>(R.id.toggleButton).setImageResource(R.drawable.toggleoff)
                toggleValue = 0;
            }
        }
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}