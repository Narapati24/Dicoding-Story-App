package com.dicoding.dicodingstoryapp.view.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.lifecycleScope
import com.dicoding.dicodingstoryapp.data.pref.UserPreferences
import com.dicoding.dicodingstoryapp.data.pref.dataStore
import com.dicoding.dicodingstoryapp.databinding.ActivitySplashBinding
import com.dicoding.dicodingstoryapp.view.home.HomeActivity
import com.dicoding.dicodingstoryapp.view.login.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            val pref = UserPreferences.getInstance(application.dataStore).getTokenUser().firstOrNull()
            if (pref != null){
                val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this@SplashActivity as Activity).toBundle())
                finish()
            } else {
                delay(1000)
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this@SplashActivity as Activity).toBundle())
                finish()
            }
        }
    }
}