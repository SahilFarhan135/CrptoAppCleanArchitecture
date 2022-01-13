package com.example.quhu.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.quhu.R
import com.example.quhu.base.BaseActivity
import com.example.quhu.databinding.ActivitySplashBinding
import com.example.quhu.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashhActivity : BaseActivity<ActivitySplashBinding>() {

    override fun layoutId(): Int = R.layout.activity_splash
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000);
    }

    override fun addObservers() = Unit
}





