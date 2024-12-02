package com.dicoding.dicodingstoryapp.view.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingstoryapp.data.Result
import com.dicoding.dicodingstoryapp.data.response.ListStoryItem
import com.dicoding.dicodingstoryapp.databinding.ActivityHomeBinding
import com.dicoding.dicodingstoryapp.helper.ListStoryAdapter
import com.dicoding.dicodingstoryapp.view.ViewModelFactory
import com.dicoding.dicodingstoryapp.view.crud.AddStoryActivity
import com.dicoding.dicodingstoryapp.view.login.LoginActivity
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        )[HomeViewModel::class.java]
        showLoading()
        viewModel.getStories()

        binding.rvStory.setHasFixedSize(true)
        viewModel.stories.observe(this){ result ->
            hideLoading()
            when (result) {
                is Result.Success -> {
                    val stories = result.data
                    showRecyclerList(stories)
                }
                is Result.Error -> {
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    //
                }
            }
        }

        binding.faUpload.setOnClickListener {
            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent)
        }

        binding.faLogout.setOnClickListener {
            lifecycleScope.launch {
                viewModel.logout()
                val intent = Intent(this@HomeActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }
        playAnimation()
    }

    private fun showRecyclerList(stories: List<ListStoryItem>) {
        binding.rvStory.layoutManager = LinearLayoutManager(this)
        val listStoryAdapter = ListStoryAdapter(stories)
        binding.rvStory.adapter = listStoryAdapter
    }

    private fun playAnimation(){
        val rv = ObjectAnimator.ofFloat(binding.rvStory, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(rv)
            start()
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading(){
        binding.progressBar.visibility = View.GONE
    }
}