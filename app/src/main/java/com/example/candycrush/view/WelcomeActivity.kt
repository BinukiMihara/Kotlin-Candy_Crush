package com.example.candycrush.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.candycrush.R
import com.example.candycrush.view.PlayActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val action = supportActionBar
        action?.hide()

        try {
            Handler().postDelayed({
                startActivity(Intent(this, PlayActivity::class.java))
            }, 3000)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
