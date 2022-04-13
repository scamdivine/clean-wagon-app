package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView


class SelectionFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val arrayAdapter: ArrayAdapter<*>
        val users = arrayOf(
            "Virat Kohli", "Rohit Sharma", "Steve Smith",
            "Kane Williamson", "Ross Taylor"
        )
        val fragmentView = inflater.inflate(R.layout.fragment_selection, container, false)
        var mowerList = fragmentView.findViewById<ListView>(R.id.mowerList)
        arrayAdapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_list_item_1, users)
        mowerList.adapter = arrayAdapter

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selection, container, false)
    }

}