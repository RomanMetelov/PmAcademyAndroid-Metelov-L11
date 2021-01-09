package com.example.pmacademyandroid_metelov_l11

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.example.pmacademyandroid_metelov_l11.databinding.ActivityNotificationsBinding
import java.util.concurrent.Executors

class NotificationsActivity : AppCompatActivity() {

    companion object {
        private const val CHANNEL_ID = "NOTIFICATIONS_CHANNEL"
        private const val NOTIFICATIONS_ACTIVITY_TAG = "NTFCTIONS_ACTIVITY_TAG"

        fun start(context: Context) {
            context.startActivity(Intent(context, NotificationsActivity::class.java))
        }
    }

    private lateinit var binding: ActivityNotificationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupListeners()
    }

    private fun setupBinding() {
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupListeners() {
        binding.btnSimpleNotification.setOnClickListener {
            Log.d(NotificationsActivity.NOTIFICATIONS_ACTIVITY_TAG, "binding.btnSimpleNotification.setOnClickListener - click")
            sendSimpleNotification()
        }
        binding.btnNotificationWithAction.setOnClickListener {
            Log.d(NotificationsActivity.NOTIFICATIONS_ACTIVITY_TAG, "binding.btnNotificationWithAction.setOnClickListener - click")
            sendNotificationWithAction()
        }
        binding.btnNotificationWithReply.setOnClickListener {
            Log.d(NotificationsActivity.NOTIFICATIONS_ACTIVITY_TAG, "binding.btnNotificationWithReply.setOnClickListener - click")
            sendNotificationWithReply()
        }
        binding.btnNotificationWithProgress.setOnClickListener {
            Log.d(NotificationsActivity.NOTIFICATIONS_ACTIVITY_TAG, "binding.btnNotificationWithProgress.setOnClickListener - click")
            sendNotificationWithProgress()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, "name", importance).apply {
                description = "descriptionText"
            }
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendSimpleNotification() {
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("My simple notification")
                .setContentText("Much longer text that cannot fit one line...")
                .setStyle(
                        NotificationCompat.BigTextStyle()
                                .bigText("Much longer text that cannot fit one line...")
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        createNotificationChannel()
        NotificationManagerCompat.from(this).notify(1, builder.build())

    }

    private fun sendNotificationWithAction() {
        val intent = Intent(this, NotificationsActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                0
        )

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("My notification with action")
                .setContentText("dori me interimo ayapare dorime ameno ameno latire")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_launcher_foreground, "Open", pendingIntent)
        createNotificationChannel()
        NotificationManagerCompat.from(this).notify(2, builder.build())
    }

    private fun sendNotificationWithReply() {
        val replyLabel: String = "REPLY"
        val remoteInput: RemoteInput = RemoteInput.Builder("KEY_TEXT_REPLY").run {
            setLabel(replyLabel)
            build()
        }

        val intent = Intent("REPLY_ACTION")
        val replyPendingIntent: PendingIntent =
                PendingIntent.getBroadcast(
                        this,
                        228,
                        intent,
                        0
                )

        val action: NotificationCompat.Action =
                NotificationCompat.Action.Builder(
                        R.drawable.ic_launcher_foreground,
                        "REPLY",
                        replyPendingIntent
                )
                        .addRemoteInput(remoteInput)
                        .build()

        val notificationWithReply =
                NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("My notification with reply")
                        .setContentText("some text inside the notification")
                        .addAction(action)
                        .setAutoCancel(true)
                        .build()

        NotificationManagerCompat.from(this)
                .notify(2, notificationWithReply)
    }

    private fun sendNotificationWithProgress() {
        val notificationId = 3
        val builder = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setContentTitle("Picture Download")
            setContentText("Download in progress...")
            setSmallIcon(R.drawable.ic_launcher_foreground)
            priority = NotificationCompat.PRIORITY_LOW
        }
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            NotificationManagerCompat.from(this).apply {
                repeat(11) {
                    builder.setProgress(100, 10 * it, false)
                    builder.setContentText("Downloading... ${it * 10}%")
                    notify(3, builder.build())
                    Thread.sleep(1000)
                }
                builder.setContentText("Download complete!")
                        .setProgress(0, 0, false)
                notify(notificationId, builder.build())
            }
        }
    }
}