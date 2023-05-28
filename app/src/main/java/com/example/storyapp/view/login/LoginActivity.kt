package com.example.storyapp.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityLoginBinding
import com.example.storyapp.view.main.MainActivity
import com.example.storyapp.view.register.RegisterActivity
import com.example.storyapp.view.splash.SplashActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
@ExperimentalPagingApi
class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val loginViewModel: LoginViewModel by viewModels ()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()


        playAnimation()
        setupAction()

    }


    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val EMAILFORMAT = Regex("[a-zA-Z0-9._]+@[a-z]+\\.+[a-z]+")
            binding.loading.visibility = View.VISIBLE

            when {
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = "Please insert your email"
                    binding.loading.visibility = View.INVISIBLE
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Please insert your password"
                    binding.loading.visibility = View.INVISIBLE
                }
                password.length < 8 -> {
                    Toast.makeText(this, "Password must or more than 8 character", Toast.LENGTH_SHORT).show()
                    binding.loading.visibility = View.INVISIBLE
                }
                !email.matches(EMAILFORMAT) -> {
                    binding.emailEditTextLayout.error = "Incorrect email format"
                    binding.loading.visibility = View.INVISIBLE
                }
                else -> {
                    lifecycleScope.launchWhenResumed {
                        launch {
                            loginViewModel.login(email, password)
                                .observe(this@LoginActivity) { result ->
                                    result.onSuccess { credential ->
                                        credential.loginResult.token.let { token ->
                                            loginViewModel.saveToken(token)
                                            binding.loading.visibility = View.INVISIBLE
                                            AlertDialog.Builder(this@LoginActivity).apply {
                                                setTitle("Yeah!")
                                                setMessage("You are successfully Login")
                                                setPositiveButton("Next") { _, _ ->
                                                    Intent(
                                                        this@LoginActivity,
                                                        MainActivity::class.java
                                                    ).also {
                                                        startActivity(it)
                                                        finish()
                                                    }
                                                }
                                                create()
                                                show()
                                            }
                                        }
                                    }
                                    result.onFailure {
                                        binding.loading.visibility = View.INVISIBLE
                                        val error = it.message
                                        Toast.makeText(
                                            this@LoginActivity,
                                            error,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        }
                    }
                }
            }
        }
    }

    private fun playAnimation() {
        val logo = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(400)
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(400)
        val msg = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(400)
        val emailText = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(400)
        val email = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(400)
        val passwordText = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(400)
        val password = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(400)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(400)
        val ask = ObjectAnimator.ofFloat(binding.tvAsk, View.ALPHA, 1f).setDuration(400)
        val goRegis = ObjectAnimator.ofFloat(binding.gotoRegister, View.ALPHA, 1f).setDuration(400)

        AnimatorSet().apply {
            playSequentially(logo,title,msg,emailText,email,passwordText,password,login,ask,goRegis)
            start()
        }
    }

    fun goToRegister(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

}
