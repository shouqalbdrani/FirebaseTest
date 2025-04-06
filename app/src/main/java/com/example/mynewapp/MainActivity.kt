package com.example.mynewapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        // Fetch the FCM token when the app starts
        fetchFCMToken()

        setContent {
            MyApp()
        }
    }

    // Fetch and log the FCM token when the app starts
    private fun fetchFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                // Log the token (you can also store it in your server or Firestore)
                println("FCM Token: $token")
            } else {
                println("Failed to fetch FCM token: ${task.exception}")
            }
        }
    }
}

@Composable
fun MyApp() {
    MaterialTheme {
        Surface {
            Text(text = "Hello World")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}
