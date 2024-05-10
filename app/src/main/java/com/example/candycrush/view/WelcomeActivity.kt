package com.example.candycrush.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.candycrush.R
import com.example.candycrush.view.PlayActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {        //  Override the onCreate method to define what happens when the activity is created

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)        // Set the layout for this activity

        val action = supportActionBar         // Hide the action bar for a cleaner look
        action?.hide()

        try {
            // Use the Handler to post a delayed runnable task
            // This task will start the PlayActivity after a 3-second delay
            Handler().postDelayed({
                startActivity(Intent(this, PlayActivity::class.java))
            }, 3000)
        } catch (e: Exception) {
            e.printStackTrace()       // Catch any exceptions that might occur during the delay
        }
    }
}
