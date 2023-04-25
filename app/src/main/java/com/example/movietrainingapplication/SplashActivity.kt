package com.example.movietrainingapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.window.SplashScreen
import androidx.annotation.NonNull
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val image = findViewById<ImageView>(R.id.imageView)
        image.alpha = 0f
        image.animate().setDuration(1500).alpha(1f).withEndAction {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}