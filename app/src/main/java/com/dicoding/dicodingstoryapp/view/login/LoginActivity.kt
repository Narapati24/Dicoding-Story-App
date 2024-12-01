package com.dicoding.dicodingstoryapp.view.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dicoding.dicodingstoryapp.MainActivity
import com.dicoding.dicodingstoryapp.R
import com.dicoding.dicodingstoryapp.databinding.ActivityLoginBinding
import com.dicoding.dicodingstoryapp.view.register.RegisterActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
    }

    private fun setupView(){
        viewModel.loginSuccess.observe(this){ success ->
            when (success) {
                true -> {
                    Toast.makeText(this, getString(R.string.login_success), Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this, MainActivity::class.java)
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
            lifecycleScope.launch {
                viewModel.login(email, password)
            }
        }
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}