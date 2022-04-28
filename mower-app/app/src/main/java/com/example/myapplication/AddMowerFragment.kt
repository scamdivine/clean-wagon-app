package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment


class AddMowerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_add_mower, container, false)
        val nameMowerButton = view.findViewById<Button>(R.id.nameMowerButton)
        val mowerName = view.findViewById<EditText>(R.id.mowerNameEditText)
        val pressedMower = arguments
        val message = pressedMower!!.getInt("pressedMower", 0)
        println(message)
        nameMowerButton.setOnClickListener{
            println(mowerName.text)
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        }
        // Inflate the layout for this fragment
        return view
    }

}