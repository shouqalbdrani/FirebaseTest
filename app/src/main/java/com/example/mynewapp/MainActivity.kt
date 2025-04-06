package com.example.mynewapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase services
        FirebaseApp.initializeApp(this)

        // Initialize Firebase Analytics
        firebaseAnalytics = Firebase.analytics

        // Log App Open event
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null)

        // Fetch FCM token
        fetchFCMToken()

        // Set Compose content
        setContent {
            MyApp(onButtonClicked = {
                logCustomEvent("button_tap", "label" to "Compose Button")
            })
        }
    }

    // Fetch and log the FCM token
    private fun fetchFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                println("FCM Token: $token")
                // Optional: log token fetch
                firebaseAnalytics.logEvent("fcm_token_received", null)
            } else {
                println("Failed to fetch FCM token: ${task.exception}")
            }
        }
    }

    // Log a custom event
    private fun logCustomEvent(eventName: String, vararg params: Pair<String, String>) {
        val bundle = Bundle().apply {
            params.forEach { putString(it.first, it.second) }
        }
        firebaseAnalytics.logEvent(eventName, bundle)
    }
}

@Composable
fun MyApp(onButtonClicked: () -> Unit) {
    MaterialTheme {
        Surface {
            Column {
                Text(text = "Hello World")
                Button(onClick = onButtonClicked) {
                    Text(text = "Track Action")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp(onButtonClicked = {})
}
