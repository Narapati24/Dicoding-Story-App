package com.dicoding.dicodingstoryapp.view.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingstoryapp.data.Result
import com.dicoding.dicodingstoryapp.data.StoryRepository
import com.dicoding.dicodingstoryapp.data.pref.UserPreferences
import com.dicoding.dicodingstoryapp.data.response.ListStoryItem
import kotlinx.coroutines.launch

class HomeViewModel(private val mApplication: Application, private val pref: UserPreferences, private val storyRepository: StoryRepository): ViewModel() {
    private var _stories = MutableLiveData<Result<List<ListStoryItem>>>()
    val stories: LiveData<Result<List<ListStoryItem>>> = _stories

    fun getStories() {
        viewModelScope.launch {
            _stories.value = storyRepository.getStories()
        }
    }

    suspend fun logout(){
        pref.deleteTokenUser()
    }
}