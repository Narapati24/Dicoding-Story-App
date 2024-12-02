package com.dicoding.dicodingstoryapp.data.di

import android.content.Context
import com.dicoding.dicodingstoryapp.data.StoryRepository
import com.dicoding.dicodingstoryapp.data.pref.UserPreferences
import com.dicoding.dicodingstoryapp.data.pref.dataStore
import com.dicoding.dicodingstoryapp.data.retrofit.ApiConfig
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): StoryRepository{
        val pref = UserPreferences.getInstance(context.dataStore)
        val user = runBlocking { pref.getTokenUser() }
        val apiService = ApiConfig.getApiService(user.toString())
        return StoryRepository.getInstance(apiService, pref)
    }
}