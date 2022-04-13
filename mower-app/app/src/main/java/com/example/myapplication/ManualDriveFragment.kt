package com.example.myapplication

import android.accessibilityservice.AccessibilityGestureEvent.CREATOR
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.getDefaultAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.BLUETOOTH_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.ParcelUuid
import android.os.Parcelable
import android.os.Parcelable.Creator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.widget.ImageButton


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
//        if(m_blueAdapter!!.isEnabled){
//            val enableBTIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//            startActivityForResult(enableBTIntent, REQUEST_ENABLE_BLUETOOTH)
//        }
        val view = inflater.inflate(R.layout.fragment_manual_drive, container, false)
        val driveForward = view.findViewById<ImageButton>(R.id.manualDriveButtonUp)
        val driveBackward = view.findViewById<ImageButton>(R.id.manualDriveButtonDown)
        val driveLeft = view.findViewById<ImageButton>(R.id.manualDriveButtonLeft)
        val driveRight = view.findViewById<ImageButton>(R.id.manualDriveButtonRight)
        driveForward.setOnClickListener {
            if (m_blueAdapter!!.isEnabled) {       //Send forward command
                Toast.makeText(
                    context,
                    "Go forward",
                    Toast.LENGTH_SHORT
                ).show()
                startAdvertising(SEND_MOWER_FORWARD)
            }
        }
        driveBackward.setOnClickListener {            //Send backward command

            if (m_blueAdapter!!.isEnabled) {
                Toast.makeText(
                    context,
                    "Go back",
                    Toast.LENGTH_SHORT
                ).show()
                startAdvertising(SEND_MOWER_BACKWARD)
            }
        }
        driveLeft.setOnClickListener {            //Send left command

            if (m_blueAdapter!!.isEnabled) {
                Toast.makeText(
                    context,
                    "Go left",
                    Toast.LENGTH_SHORT
                ).show()
                startAdvertising(SEND_MOWER_LEFT)
            }
        }
        driveRight.setOnClickListener {            //Send right command

            if (m_blueAdapter!!.isEnabled) {
                Toast.makeText(
                    context,
                    "Go right",
                    Toast.LENGTH_SHORT
                ).show()
                startAdvertising(SEND_MOWER_RIGHT)
            }
        }
        return view
    }

//    @Deprecated("Deprecated in Java")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode == REQUEST_ENABLE_BLUETOOTH) {
//            if (resultCode == Activity.RESULT_OK) {
//                if (m_blueAdapter!!.isEnabled) {
//                    Toast.makeText(context, "Bluetooth is enabled", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(context, "Bluetooth is disabled", Toast.LENGTH_LONG).show()
//                }
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                Toast.makeText(context, "Bluetooth enabling was cancelled", Toast.LENGTH_LONG)
//                    .show()
//            }
//        }
//    }
    @SuppressLint("MissingPermission")
    private fun startAdvertising(direction: Int) {
        m_blueAdapter?.bluetoothLeAdvertiser?.let {
            val setting = AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
                .setConnectable(false)
                .setTimeout(0)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
                .build()

            val data = AdvertiseData.Builder()
                .setIncludeDeviceName(true)
                .setIncludeTxPowerLevel(true)
                //.addServiceUuid(ParcelUuid.fromString("00001827-0000-1000-8000-00805F9B34FB"))
                .addServiceUuid(ParcelUuid.fromString("00001827-000$direction-1000-8000-00805F9B34FB"))
                .build()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val advertiseCallback = null
                it.startAdvertising(setting, data, advertiseCallback)
            }
            Toast.makeText(context, "Advertising Started", Toast.LENGTH_SHORT).show()
        }
    }
}

