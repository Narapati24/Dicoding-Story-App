package com.dicoding.dicodingstoryapp.view.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingstoryapp.data.Result
import com.dicoding.dicodingstoryapp.data.StoryRepository
import com.dicoding.dicodingstoryapp.data.response.DetailStoryResponse
import kotlinx.coroutines.launch

class DetailViewModel(private val mApplication: Application, private val storyRepository: StoryRepository) : ViewModel() {
    private var _story = MutableLiveData<Result<DetailStoryResponse>>()
    val story: LiveData<Result<DetailStoryResponse>> = _story

    fun getStory(id: String) =
        viewModelScope.launch {
            _story.value = storyRepository.getStory(id)
        }
}