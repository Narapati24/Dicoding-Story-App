package com.dicoding.dicodingstoryapp.view.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dicoding.dicodingstoryapp.R
import com.dicoding.dicodingstoryapp.databinding.ActivityRegisterBinding
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this)[RegisterViewModel::class.java]
    }
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupButton()
    }

    private fun setupView(){
        viewModel.registerSuccess.observe(this){ success ->
            hideLoading()
            when (success) {
                true -> {
                    Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT)
                        .show()
                    finish()
                    viewModel.resetRegisterStatus()
                }

                false -> {
                    Toast.makeText(this, viewModel.errorResponse, Toast.LENGTH_SHORT)
                        .show()
                    viewModel.resetRegisterStatus()
                }

                null -> {
                    //
                }
            }
        }
    }

    private fun setupButton(){
        binding.signupButton.setOnClickListener {
            showLoading()
            lifecycleScope.launch {
                viewModel.register(
                    binding.edRegisterName.text.toString(),
                    binding.edRegisterEmail.text.toString(),
                    binding.edRegisterPassword.text.toString()
                )
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.signupButton.isEnabled = false
    }

    private fun hideLoading(){
        binding.progressBar.visibility = View.GONE
        binding.signupButton.isEnabled = true
    }
}