package com.example.newspulse.ui.components.profileoptionsScreens

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.newspulse.MainActivity
import com.example.newspulse.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyNotification : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("FCM_DEBUG", "New Token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.e("FCM_DEBUG", "!!! MESSAGE RECEIVED !!!")
        Log.e("FCM_DEBUG", "Data: ${remoteMessage.data}")

        val title = remoteMessage.data["title"] ?: remoteMessage.notification?.title
        val body = remoteMessage.data["body"] ?: remoteMessage.notification?.body
        val image = remoteMessage.data["image"]
        val description = remoteMessage.data["description"]
        val content = remoteMessage.data["content"]

        if (title != null || body != null) {
            showNotification(title, body, image, description, content)
        }
    }

    private fun showNotification(
        title: String?,
        body: String?,
        image: String? = null,
        description: String? = null,
        content: String? = null
    ) {
        val channelId = "news_pulse_alerts"
        val channelName = "News Updates"

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                this.description = "News Pulse Notification Channel"
            }
            notificationManager.createNotificationChannel(channel)
        }
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("title", title)
            putExtra("image", image)
            putExtra("description", description)
            putExtra("content", content)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder)
        Log.e("FCM_DEBUG", "Notification posted to tray")
    }
}