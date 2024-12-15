package com.dicoding.dicodingstoryapp.view.maps

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingstoryapp.data.Result
import com.dicoding.dicodingstoryapp.data.StoryRepository
import com.dicoding.dicodingstoryapp.data.response.ListStoryItem
import kotlinx.coroutines.launch

class MapsViewModel(private val mApplication: Application, private val storyRepository: StoryRepository) : ViewModel() {
    private var _stories = MutableLiveData<Result<List<ListStoryItem>>>()
    val stories: LiveData<Result<List<ListStoryItem>>> = _stories
    fun getStoriesWithLocation(){
        viewModelScope.launch {
            _stories.value = storyRepository.getStoriesWithLocation()
        }
    }
}