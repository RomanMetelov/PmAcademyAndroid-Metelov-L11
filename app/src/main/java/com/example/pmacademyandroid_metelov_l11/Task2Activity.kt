package com.example.pmacademyandroid_metelov_l11

import android.content.ContentProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.pmacademyandroid_metelov_l11.databinding.ActivityNotificationsBinding
import com.example.pmacademyandroid_metelov_l11.databinding.ActivityNotificationsBinding.inflate
import com.example.pmacademyandroid_metelov_l11.databinding.ActivityStartBinding
import com.example.pmacademyandroid_metelov_l11.databinding.ActivityTask2Binding

class Task2Activity : AppCompatActivity() {

    companion object {
        private const val TASK_2_TAG = "TASK_2_TAG"

        fun start(context: Context) {
            context.startActivity(Intent(context, Task2Activity::class.java))
        }
    }

    private lateinit var binding: ActivityTask2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupListeners()
    }

    private fun setupBinding() {
        binding = ActivityTask2Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupListeners() {
        binding.btnExtras.setOnClickListener {
            Log.d(TASK_2_TAG, "binding.btnExtras.setOnClickListener - click")
            openNewActivityWithExtras()
        }
        binding.btnMaps.setOnClickListener {
            Log.d(TASK_2_TAG, "binding.btnMaps.setOnClickListener - click")
            openMapWithCoordinates()
        }
    }

    private fun openNewActivityWithExtras() {
        ActivityWithExtras.start(this, "text parameter", 42)
    }

    private fun openMapWithCoordinates() {
        val coordinatesUri = Uri.parse(
                "geo:53.430746,-2.9615301?q="
                        + Uri.encode("Anfield Stadium, Anfield Road, Anfield, Liverpool, UK")
        )

        val mapIntent = Intent(Intent.ACTION_VIEW, coordinatesUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        mapIntent.resolveActivity(this.packageManager)?.let {
            startActivity(mapIntent)
        }
    }
}