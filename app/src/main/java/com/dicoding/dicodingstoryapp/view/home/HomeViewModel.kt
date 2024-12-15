package com.dicoding.dicodingstoryapp.view.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.dicodingstoryapp.data.Result
import com.dicoding.dicodingstoryapp.data.StoryRepository
import com.dicoding.dicodingstoryapp.data.pref.UserPreferences
import com.dicoding.dicodingstoryapp.data.response.ListStoryItem
import kotlinx.coroutines.launch

class HomeViewModel(private val mApplication: Application, private val pref: UserPreferences, private val storyRepository: StoryRepository): ViewModel() {

    val stories: LiveData<PagingData<ListStoryItem>> = storyRepository.getStories().cachedIn(viewModelScope)

    suspend fun logout(){
        pref.deleteTokenUser()
    }
}