package com.example.pmacademyandroid_metelov_l11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pmacademyandroid_metelov_l11.databinding.ActivityStartBinding


class StartActivity : AppCompatActivity() {

    companion object {
        private const val START_ACTIVITY_TAG = "START_ACTIVITY_TAG"
    }

    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupListeners()
    }

    private fun setupBinding() {
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupListeners() {
        binding.btnNotifications.setOnClickListener {
            Log.d(START_ACTIVITY_TAG, "binding.btnNotifications.setOnClickListener - click")
            NotificationsActivity.start(this)
        }
        binding.btnTask2.setOnClickListener {
            Log.d(START_ACTIVITY_TAG, "binding.btnTask2.setOnClickListener - click")
            Task2Activity.start(this)
        }
        binding.btnReceivers.setOnClickListener {
            Log.d(START_ACTIVITY_TAG, "binding.btnReceivers.setOnClickListener - click")
            ReceiversActivity.start(this)
        }
    }
}