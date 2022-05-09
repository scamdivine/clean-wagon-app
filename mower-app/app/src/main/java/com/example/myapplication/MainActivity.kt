@file:Suppress("DEPRECATION")

package com.example.myapplication

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.BluetoothControllerActivity.Companion.m_bluetoothAdapter
import com.example.myapplication.BluetoothControllerActivity.Companion.m_bluetoothSocket
import com.example.myapplication.BluetoothControllerActivity.Companion.m_isConnected
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.IOException

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ConnectToDevice(this).execute()
        val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragment)
        navView.setupWithNavController(navController)
        val returnButton = findViewById<ImageView>(R.id.returnArrow)
        returnButton.setOnClickListener{
            startActivity(Intent(this, SelectionActivity::class.java))
        }
        val bundle = Bundle().get("fragmentSelection")
        if(bundle != null){
            selectFragment(bundle)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        BluetoothControllerActivity().disconnect()
    }

    private class ConnectToDevice(c: Context) : AsyncTask<Void, Void, String>() {
        private var connectSuccess: Boolean = true
        private val context: Context
        init {
            this.context = c
        }

        override fun onPreExecute() {
            super.onPreExecute()
            BluetoothControllerActivity.m_progress = ProgressDialog.show(context, "Connecting...", "please wait")
        }

        @RequiresApi(Build.VERSION_CODES.S)
        @SuppressLint("MissingPermission")
        override fun doInBackground(vararg p0: Void?): String? {
            try {
                if (m_bluetoothSocket == null || !m_isConnected) {
                    m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val device: BluetoothDevice = m_bluetoothAdapter.getRemoteDevice(deviceBluetoothAddress)
                    m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    m_bluetoothSocket!!.connect()
                }
            } catch (e: IOException) {
                connectSuccess = false
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (!connectSuccess) {
                Log.i("data", "couldn't connect")
            } else {
                BluetoothControllerActivity.m_isConnected = true
            }
            BluetoothControllerActivity.m_progress.dismiss()
        }
    }

    private fun selectFragment(bundle: Any){
        when (bundle) {
            1 -> findNavController(R.id.fragment).navigate(R.id.ManualDrive)
            2 -> findNavController(R.id.fragment).navigate(R.id.MapFragment)
            3 -> findNavController(R.id.fragment).navigate(R.id.SettingsFragment)
        }
    }
}
