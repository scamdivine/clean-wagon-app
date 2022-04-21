package com.example.myapplication

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.coroutines.NonDisposableHandle.parent


class SelectionFragment : Fragment() {

    private lateinit var binding: SelectionFragment
    private lateinit var mowerArrayList : ArrayList<Mower>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_selection, container, false)
        val arrayAdapter: ArrayAdapter<*>
        val addMowerFragment = AddMowerFragment()
        val names = arrayOf(
            "Automower 105", "Automower 305", "Automower 310",
            "Automower 315", "Automower 420", "Automower 440",
            "Automower 405", "Automower 415"
        )
        val mowerImages = arrayOf( R.drawable.mower_105,R.drawable.mower_105,R.drawable.mower_105,
            R.drawable.mower_105,R.drawable.mower_105,R.drawable.mower_105,R.drawable.mower_105,R.drawable.mower_105,)

        mowerArrayList = ArrayList()

        for ( i in names.indices) {
            val mower = Mower(names[i], mowerImages[i])
            mowerArrayList.add(mower)
        }

        val fragmentView = inflater.inflate(R.layout.fragment_selection, container, false)
        var mowerList = view.findViewById<ListView>(R.id.mowerList)
        val thisContext = activity
        mowerList.isClickable = true
        mowerList.adapter = thisContext?.let { MowerAdapter(it, mowerArrayList) }
        mowerList.setOnItemClickListener { arrayAdapter, view, i, l ->
            val bundle = Bundle()
            bundle.putInt("pressedMower", i)
            addMowerFragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction().add(R.id.container, addMowerFragment).commit()
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()

        }
        return view
    }
}