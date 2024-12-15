package com.dicoding.dicodingstoryapp.data

import com.dicoding.dicodingstoryapp.data.response.AddStoryResponse
import com.dicoding.dicodingstoryapp.data.response.DetailStoryResponse
import com.dicoding.dicodingstoryapp.data.response.ListStoryItem
import com.dicoding.dicodingstoryapp.data.retrofit.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository private constructor(
    private val apiService: ApiService
){
    suspend fun getStoriesWithLocation(): Result<List<ListStoryItem>> {
        return try {
            val response = apiService.getStoriesWithLocation()
            if (!response.error!!) {
                Result.Success(response.listStory)
            } else {
                Result.Error(response.message ?: "Unknown error")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error")
        }
    }

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
            apiService: ApiService
        ): StoryRepository =
            instance ?: synchronized(this){
                instance ?: StoryRepository(
                    apiService
                )
            }.also { instance = it }
    }
}