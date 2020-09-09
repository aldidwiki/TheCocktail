package com.aldidwikip.thecocktail.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Intent(this@SplashActivity, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}
