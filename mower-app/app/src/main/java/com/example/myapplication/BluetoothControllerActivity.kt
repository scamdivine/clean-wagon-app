package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException
import java.util.*


lateinit var bluetoothManager : BluetoothManager


@Suppress("DEPRECATION")
class BluetoothControllerActivity : AppCompatActivity() {
    companion object {
        var m_myUUID: UUID = UUID.fromString("7be1fcb3-5776-42fb-91fd-2ee7b5bbb86d")
        var m_bluetoothSocket: BluetoothSocket? = null
        lateinit var m_progress: ProgressDialog
        lateinit var m_bluetoothAdapter: BluetoothAdapter
        var m_isConnected: Boolean = false
        lateinit var m_address: String
    }
    val SEND_MOWER_FORWARD = "FORWARD"
    val SEND_MOWER_BACKWARD = "BACKWARD"
    val SEND_MOWER_LEFT = "LEFT"
    val SEND_MOWER_RIGHT = "RIGHT"
    val SEND_MOWER_STOP = "STOPMOVING"
    val SEND_MANUAL_CONNECT = "MANUALMODE"
    val SEND_MANUAL_DISCONNECT = "MANUALDISCONNECT"
    val SEND_START_AUTO_MODE = "AUTOMODE"
    val SEND_STOP_AUTO_MODE = "STOPAUTOMODE"
    private val TEST_BLUETOOTH_SCAN = 123
    private val TEST_BLUETOOTH_CONNECT = 113

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_controller)
        m_address = deviceBluetoothAddress
        bluetoothManager = this.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        checkPermissions(Manifest.permission.BLUETOOTH_SCAN, TEST_BLUETOOTH_SCAN)
        checkPermissions(Manifest.permission.BLUETOOTH_CONNECT, TEST_BLUETOOTH_CONNECT)
        ConnectToDevice(this).execute()
        //sendCommand(SEND_MANUAL_CONNECT)
        findViewById<ImageButton>(R.id.manualDriveButtonUp).setOnClickListener{sendCommand(SEND_MOWER_FORWARD)}
        findViewById<ImageButton>(R.id.manualDriveButtonDown).setOnClickListener{sendCommand(SEND_MOWER_BACKWARD)}
        findViewById<ImageButton>(R.id.manualDriveButtonLeft).setOnClickListener{sendCommand(SEND_MOWER_LEFT)}
        findViewById<ImageButton>(R.id.manualDriveButtonRight).setOnClickListener{sendCommand(SEND_MOWER_RIGHT)}
        findViewById<ImageButton>(R.id.manualDriveStop).setOnClickListener{sendCommand(SEND_MOWER_STOP)}
        findViewById<ImageView>(R.id.returnArrow).setOnClickListener{
            sendCommand(SEND_MANUAL_DISCONNECT)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


    fun sendCommand(input: String) {
        if (m_bluetoothSocket != null) {
            try{
                m_bluetoothSocket!!.outputStream.write(input.toByteArray())
                Toast.makeText(this, "successfully sent " + input, Toast.LENGTH_SHORT).show()
            } catch(e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun disconnect() {
        if (m_bluetoothSocket != null) {
            try {
                m_bluetoothSocket!!.close()
                m_bluetoothSocket = null
                m_isConnected = false
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        finish()
    }
    private class ConnectToDevice(c: Context) : AsyncTask<Void, Void, String>() {
        private var connectSuccess: Boolean = true
        private val context: Context
        init {
            this.context = c
        }

        override fun onPreExecute() {
            super.onPreExecute()
            m_progress = ProgressDialog.show(context, "Connecting...", "please wait")
        }

        @RequiresApi(Build.VERSION_CODES.S)
        @SuppressLint("MissingPermission")
        override fun doInBackground(vararg p0: Void?): String? {
            try {

                if (m_bluetoothSocket == null || !m_isConnected) {
                    m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val device: BluetoothDevice = m_bluetoothAdapter.getRemoteDevice(
                        m_address)
                    m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                    //BluetoothManager.adapter.cancelDiscovery()
                    //BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
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
                m_isConnected = true
            }
            m_progress.dismiss()
        }
    }
    fun waitForAck() {
        val buffer = ByteArray(256)
        var bytes = 0
        if(m_isConnected) {
            val inputs = m_bluetoothSocket!!.inputStream.read(buffer)
            while (bytes == 0) {
                bytes = inputs
            }
        }
    }
    fun checkPermissions(permission: String, requestCode:Int){
        if(ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        }
        else{
            Toast.makeText(this, "Permission already granted!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == TEST_BLUETOOTH_SCAN){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "SCAN Permission granted", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this, "SCAN Permission NOT granted", Toast.LENGTH_LONG).show()
            }
        }
        else if(requestCode == TEST_BLUETOOTH_CONNECT){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "SCAN Permission granted", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this, "SCAN Permission NOT granted", Toast.LENGTH_LONG).show()
            }
        }

    }

}