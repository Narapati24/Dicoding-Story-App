package com.dicoding.dicodingstoryapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.dicodingstoryapp.data.pref.UserPreferences
import com.dicoding.dicodingstoryapp.data.response.ListStoryItem
import com.dicoding.dicodingstoryapp.data.response.StoryResponse
import com.dicoding.dicodingstoryapp.data.retrofit.ApiService

class StoryRepository private constructor(
    private val apiService: ApiService,
    private val prev: UserPreferences
){
    private val result = MediatorLiveData<Result<List<StoryResponse>>>()

    suspend fun getStories(): Result<List<ListStoryItem>> {
        return try {
            val response = apiService.getStories("Bearer ${prev.getTokenUser()}")
            if (!response.error!!) {
                Result.Success(response.listStory)
            } else {
                Result.Error(response.message ?: "Unknown error")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error")
        }
    }

    companion object{
        @Volatile
        private var instance: StoryRepository? = null

        fun getInstance(
            apiService: ApiService,
            prev: UserPreferences
        ): StoryRepository =
            instance ?: synchronized(this){
                instance ?: StoryRepository(
                    apiService,
                    prev
                )
            }.also { instance = it }
    }
}