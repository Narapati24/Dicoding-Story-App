package com.dicoding.dicodingstoryapp.view.login

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dicoding.dicodingstoryapp.R
import com.dicoding.dicodingstoryapp.databinding.ActivityLoginBinding
import com.dicoding.dicodingstoryapp.view.LoginViewModelFactory
import com.dicoding.dicodingstoryapp.view.home.HomeActivity
import com.dicoding.dicodingstoryapp.view.register.RegisterActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupView(){
        viewModel = ViewModelProvider(this, LoginViewModelFactory.getInstance(
            this
        ))[LoginViewModel::class.java]

        viewModel.loginSuccess.observe(this){ success ->
            hideLoading()
            when (success) {
                true -> {
                    Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    viewModel.resetLoginStatus()
                }

                false -> {
                    Toast.makeText(this, viewModel.errorMassage, Toast.LENGTH_SHORT)
                        .show()
                    viewModel.resetLoginStatus()
                }

                null -> {
                    //
                }
            }
        }
    }

    private fun setupAction(){
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()
            showLoading()
            lifecycleScope.launch {
                viewModel.login(email, password)
            }
        }
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.loginButton.isEnabled = false
    }

    private fun hideLoading(){
        binding.progressBar.visibility = View.GONE
        binding.loginButton.isEnabled = true
    }

    private fun playAnimation(){
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }
}