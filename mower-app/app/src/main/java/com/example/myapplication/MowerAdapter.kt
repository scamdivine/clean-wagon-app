package com.example.myapplication

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ContentView
import androidx.fragment.app.FragmentActivity
import java.security.AccessControlContext

class MowerAdapter(private val context: FragmentActivity, private val arrayList: ArrayList<Mower>) :
    ArrayAdapter<Mower>(context, R.layout.mower_list_item, arrayList) {

        override fun getView(position: Int, contentView: View?, parent:ViewGroup): View {
            val inflater : LayoutInflater = LayoutInflater.from(context)
            val view : View = inflater.inflate(R.layout.mower_list_item, null)

            val imageView : ImageView = view.findViewById(R.id.mowerImage)
            val name : TextView = view.findViewById(R.id.mowerName)

            imageView.setImageResource(arrayList[position].imageId)
            name.text = arrayList[position].name

            return view
        }
}