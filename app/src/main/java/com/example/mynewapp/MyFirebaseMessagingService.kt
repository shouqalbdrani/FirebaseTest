package com.example.mynewapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    // Triggered when a new FCM token is generated
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Generated Token: $token")
    }

    // Triggered when a push notification is received
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val notificationBody = remoteMessage.notification?.body
        if (notificationBody != null) {
            // Show a notification in the foreground
            showNotification(notificationBody)
        }
    }

    private fun showNotification(message: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // For Android 8.0 (API level 26) and above, you need to create a notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "default_channel", // ID of the channel
                "Default Notifications", // Name of the channel
                NotificationManager.IMPORTANCE_DEFAULT // Importance level
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Build the notification
        val notification = NotificationCompat.Builder(this, "default_channel")
            .setContentTitle("New Notification")
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()

        // Display the notification
        notificationManager.notify(0, notification)
    }
}
