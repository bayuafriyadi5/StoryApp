package com.example.storyapp.view.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.paging.ExperimentalPagingApi
import com.example.storyapp.databinding.ActivitySplashBinding
import com.example.storyapp.data.local.datastore.AuthPreferences
import com.example.storyapp.view.login.LoginActivity
import com.example.storyapp.view.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalPagingApi
class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT = 4000 //3 seconds

    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    private val splashViewModel: SplashViewModel by viewModels ()
    private var token: String = "null"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        splashViewModel.checkIfTokenAvailable().observe(this) { token ->
            this.token = token ?: "null"

        }

        supportActionBar?.hide()
        Handler().postDelayed(Runnable {
            checkIfSessionValid()
            finish()
        }, SPLASH_TIME_OUT.toLong())

        playAnimation()
    }

    private fun checkIfSessionValid() {
            if (token == "null") {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                splashViewModel.setFirstTime(true)
                finish()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                splashViewModel.setFirstTime(false)
                finish()
            }

    }


    private fun playAnimation() {
        val logo = ObjectAnimator.ofFloat(binding.splashLogo, View.ALPHA, 1f).setDuration(1000)
        AnimatorSet().apply {
            playSequentially(logo)
            start()
        }
    }
}