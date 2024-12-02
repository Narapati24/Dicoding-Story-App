package com.dicoding.dicodingstoryapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.dicodingstoryapp.data.pref.UserPreferences
import com.dicoding.dicodingstoryapp.data.response.AddStoryResponse
import com.dicoding.dicodingstoryapp.data.response.DetailStoryResponse
import com.dicoding.dicodingstoryapp.data.response.ListStoryItem
import com.dicoding.dicodingstoryapp.data.response.StoryResponse
import com.dicoding.dicodingstoryapp.data.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class StoryRepository private constructor(
    private val apiService: ApiService,
    private val prev: UserPreferences
){
    private val result = MediatorLiveData<Result<List<StoryResponse>>>()

    suspend fun getStories(): Result<List<ListStoryItem>> {
        return try {
            val response = apiService.getStories()
            if (!response.error!!) {
                Result.Success(response.listStory)
            } else {
                Result.Error(response.message ?: "Unknown error")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun getStory(id: String): Result<DetailStoryResponse>{
        return try {
            val response = apiService.getDetailStory(id)
            if (!response.error!!) {
                Result.Success(response)
            } else {
                Result.Error(response.message ?: "Unknown error")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun addStory(description: RequestBody, photo: MultipartBody.Part): Result<AddStoryResponse> {
        return try {
            val response = apiService.addStory(description, photo)
            if (!response.error!!) {
                Result.Success(response)
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