package com.example.myapplication

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.getDefaultAdapter
import android.bluetooth.le.AdvertiseSettings
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment


class ManualDriveFragment: Fragment() {
    var m_blueAdapter: BluetoothAdapter? = null
    val REQUEST_ENABLE_BLUETOOTH = 1
    val SEND_MOWER_FORWARD = 1
    val SEND_MOWER_BACKWARD = 2
    val SEND_MOWER_LEFT = 3
    val SEND_MOWER_RIGHT = 4

    @Suppress("DEPRECATION")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        AdvertiseSettings.Builder()
        m_blueAdapter = getDefaultAdapter()
        val view = inflater.inflate(R.layout.fragment_manual_drive, container, false)
        val driveStop = view.findViewById<ImageButton>(R.id.manualDriveStop)
        driveStop.setOnClickListener{
            val intent = Intent(activity, BluetoothActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}

