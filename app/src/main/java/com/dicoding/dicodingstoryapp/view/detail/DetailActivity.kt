package com.dicoding.dicodingstoryapp.view.detail

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.dicodingstoryapp.data.Result
import com.dicoding.dicodingstoryapp.databinding.ActivityDetailBinding
import com.dicoding.dicodingstoryapp.helper.getCityName
import com.dicoding.dicodingstoryapp.view.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(ID)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        )[DetailViewModel::class.java]
        viewModel.getStory(id!!)

        setupView()
    }

    private fun setupView() {
        viewModel.story.observe(this){ result ->
            when(result){
                is Result.Error -> {
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
                Result.Loading -> {
                    //
                }
                is Result.Success -> {
                    val story = result.data
                    Glide.with(this)
                        .load(story.story?.photoUrl)
                        .into(binding.ivDetailPhoto)
                    binding.tvDetailName.text = story.story?.name
                    binding.tvDetailDescription.text = story.story?.description
                    binding.tvCity.text = getCityName(story.story?.lat, story.story?.lon, this)
                }
            }
        }
    }

    companion object{
        const val ID = "id"
    }
}