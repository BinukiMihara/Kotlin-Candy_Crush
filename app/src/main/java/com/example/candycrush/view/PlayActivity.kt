package com.example.candycrush.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.candycrush.MainActivity
import com.example.candycrush.R

class PlayActivity : AppCompatActivity() {
    private lateinit var playBtn :ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        val action = supportActionBar
        action?.hide()
        playBtn = findViewById(R.id.playBtn)
        playBtn.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}