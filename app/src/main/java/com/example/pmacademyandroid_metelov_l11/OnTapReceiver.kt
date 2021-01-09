package com.example.pmacademyandroid_metelov_l11

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class OnTapReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.v("ON_TAP_RECEIVER_TAG", "Button tap detected!")
    }
}