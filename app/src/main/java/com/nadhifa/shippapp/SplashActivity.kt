package com.nadhifa.shippapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.nadhifa.shippapp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)

        binding.ivLogo.startAnimation(logoAnimation)
        val splashScreenTimeOut = 4000

        val home = Intent(this, MainActivity::class.java)

        Handler().postDelayed({
            startActivity(home)
            finish()
        }, splashScreenTimeOut.toLong())
    }
}