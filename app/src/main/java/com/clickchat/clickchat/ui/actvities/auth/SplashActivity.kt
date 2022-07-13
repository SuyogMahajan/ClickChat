package com.clickchat.clickchat.ui.actvities.auth

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.clickchat.clickchat.databinding.ActivitySplashBinding
import com.clickchat.clickchat.ui.actvities.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.collection.LLRBNode


class SplashActivity : AppCompatActivity() {
    val auth by lazy {
        FirebaseAuth.getInstance()
    }

    private lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var i:Intent
        if (auth.currentUser == null) {
            i = Intent(this, LoginActivity::class.java)
        } else {
            i = Intent(this, MainActivity::class.java)
        }

        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(i)
        },2500)

    }
}