package com.dicoding.dicodingstoryapp.view.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
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
import com.dicoding.dicodingstoryapp.view.maps.MapsActivity
import kotlinx.coroutines.delay
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
            startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this as Activity).toBundle())
        }

        binding.faLogout.setOnClickListener {
            lifecycleScope.launch {
                viewModel.logout()
                val intent = Intent(this@HomeActivity, LoginActivity::class.java)
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this@HomeActivity as Activity).toBundle())
                delay(1000)
                finish()
            }
        }

        binding.faMaps.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this as Activity).toBundle())
        }
    }

    private fun showRecyclerList(stories: List<ListStoryItem>) {
        binding.rvStory.layoutManager = LinearLayoutManager(this)
        val listStoryAdapter = ListStoryAdapter(stories)
        binding.rvStory.adapter = listStoryAdapter
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading(){
        binding.progressBar.visibility = View.GONE
    }
}