package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R.id.select_device_list
var deviceBluetoothAddress = ""
lateinit var bluetoothAdapter : BluetoothAdapter

class BluetoothActivity : AppCompatActivity() {
    private var m_bluetoothAdapter: BluetoothAdapter? = null
    private lateinit var m_pairedDevices: Set<BluetoothDevice>
    private val REQUEST_ENABLE_BLUETOOTH = 1

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)

        m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        bluetoothAdapter = (m_bluetoothAdapter)!!
        if(m_bluetoothAdapter == null) {
            Toast.makeText(this, "this device doesn't support bluetooth", Toast.LENGTH_SHORT).show()
            return
        }
        if(!m_bluetoothAdapter!!.isEnabled) {
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
        }

        findViewById<Button>(R.id.select_device_refresh).setOnClickListener{ pairedDeviceList() }

    }

    @SuppressLint("MissingPermission")
    private fun pairedDeviceList() {
        m_pairedDevices = m_bluetoothAdapter!!.bondedDevices
        val list : ArrayList<BluetoothDevice> = ArrayList()

        if (!m_pairedDevices.isEmpty()) {
            for (device: BluetoothDevice in m_pairedDevices) {
                list.add(device)
                Log.i("device", ""+device)
            }
        } else {
            Toast.makeText(this, "no paired bluetooth devices found", Toast.LENGTH_SHORT).show()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        findViewById<ListView>(select_device_list).adapter = adapter
        findViewById<ListView>(select_device_list).onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val device: BluetoothDevice = list[position]
            deviceBluetoothAddress = device.address
            Log.i("The BTaddr is:", deviceBluetoothAddress)
            Toast.makeText(this, "BT addr is:" + deviceBluetoothAddress, Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
            if (resultCode == Activity.RESULT_OK) {
                if (m_bluetoothAdapter!!.isEnabled) {
                    Toast.makeText(this, "Bluetooth has been enabled", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Bluetooth has been disabled", Toast.LENGTH_SHORT).show()
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Bluetooth enabling has been canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
