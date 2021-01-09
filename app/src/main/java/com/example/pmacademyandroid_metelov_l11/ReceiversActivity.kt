package com.example.pmacademyandroid_metelov_l11

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pmacademyandroid_metelov_l11.databinding.ActivityNotificationsBinding
import com.example.pmacademyandroid_metelov_l11.databinding.ActivityReceiversBinding

class ReceiversActivity : AppCompatActivity() {

    companion object {
        private const val TAP_ACTION = "TAP_ACTION"

        fun start(context: Context) {
            context.startActivity(Intent(context, ReceiversActivity::class.java))
        }
    }

    private lateinit var binding: ActivityReceiversBinding
    private val onTapReceiver = OnTapReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        this.registerReceiver(onTapReceiver, IntentFilter(TAP_ACTION))
        this.registerReceiver(
                connectionReceiver,
                IntentFilter("android.net.wifi.WIFI_STATE_CHANGED")
        )
    }

    override fun onPause() {
        super.onPause()
        this.unregisterReceiver(onTapReceiver)
        this.unregisterReceiver(connectionReceiver)
    }

    private fun setupBinding() {
        binding = ActivityReceiversBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private val connectionReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val wifiStateExtra = intent?.getIntExtra(
                    WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN
            )
            when (wifiStateExtra) {
                WifiManager.WIFI_STATE_ENABLED -> {
                    binding.connectionText.text = "Wi-FI is on"
                }
                WifiManager.WIFI_STATE_DISABLED -> {
                    binding.connectionText.text = "Wi-FI is off"
                }
            }
        }
    }

    private fun setupListeners() {
        binding.bSender.setOnClickListener {
            Intent().also {
                it.action = TAP_ACTION
                this.sendBroadcast(it)
            }
        }
    }
}