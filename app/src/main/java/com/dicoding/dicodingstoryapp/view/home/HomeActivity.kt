package com.dicoding.dicodingstoryapp.view.home

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingstoryapp.R
import com.dicoding.dicodingstoryapp.data.Result
import com.dicoding.dicodingstoryapp.data.StoryRepository
import com.dicoding.dicodingstoryapp.data.di.Injection
import com.dicoding.dicodingstoryapp.data.pref.UserPreferences
import com.dicoding.dicodingstoryapp.data.pref.dataStore
import com.dicoding.dicodingstoryapp.data.response.ListStoryItem
import com.dicoding.dicodingstoryapp.data.retrofit.ApiConfig
import com.dicoding.dicodingstoryapp.data.retrofit.ApiService
import com.dicoding.dicodingstoryapp.databinding.ActivityHomeBinding
import com.dicoding.dicodingstoryapp.helper.ListStoryAdapter
import com.dicoding.dicodingstoryapp.view.ViewModelFactory
import com.dicoding.dicodingstoryapp.view.ViewModelFactory2
import com.dicoding.dicodingstoryapp.view.crud.AddStoryActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory2.getInstance(this)
        )[HomeViewModel::class.java]

        binding.rvStory.setHasFixedSize(true)
        viewModel.stories.observe(this){ result ->
            when (result) {
                is Result.Success -> {
                    val stories = result.data
                    showRecyclerList(stories)
                }
                is Result.Error -> {
                    //
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
    }

    private fun showRecyclerList(stories: List<ListStoryItem>) {
        binding.rvStory.layoutManager = LinearLayoutManager(this)
        val listStoryAdapter = ListStoryAdapter(stories)
        binding.rvStory.adapter = listStoryAdapter
    }
}