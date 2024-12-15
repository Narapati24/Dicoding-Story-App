package com.dicoding.dicodingstoryapp.view.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
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
            ViewModelFactory.getInstance(application)
        )[HomeViewModel::class.java]

        binding.rvStory.setHasFixedSize(true)
        showRecyclerList()

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

    private fun showRecyclerList() {
        binding.rvStory.layoutManager = LinearLayoutManager(this)
        val listStoryAdapter = ListStoryAdapter()
        binding.rvStory.adapter = listStoryAdapter
        viewModel.stories.observe(this){
            listStoryAdapter.submitData(lifecycle, it)
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading(){
        binding.progressBar.visibility = View.GONE
    }
}