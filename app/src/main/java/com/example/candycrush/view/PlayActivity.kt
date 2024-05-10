package com.example.candycrush.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.candycrush.MainActivity
import com.example.candycrush.R

class PlayActivity : AppCompatActivity() {
    private lateinit var playBtn :ImageView
    private lateinit var inputText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        // Initialize input field for username
        inputText = findViewById(R.id.inputText)

        // Hide the action bar for a cleaner look
        val action = supportActionBar
        action?.hide()

        playBtn = findViewById(R.id.playBtn)   // Initialize play button
        playBtn.setOnClickListener{          // Set click listener for the play button
            if (inputText?.text.isNullOrEmpty()) {       // Check if username is entered
                Toast.makeText(this, "First Enter Name", Toast.LENGTH_SHORT).show()      // Display toast message if username is empty
            } else {
                val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)   // Get a reference to the SharedPreferences object
                val editor = sharedPreferences.edit()          // Get an editor to modify the SharedPreferences
                editor.putString("UserName", inputText.text.toString())          // Put the username in the SharedPreferences
                editor.apply()

                startActivity(Intent(this, MainActivity::class.java))      // Start the MainActivity (game screen)
            }

            }
        }
}
