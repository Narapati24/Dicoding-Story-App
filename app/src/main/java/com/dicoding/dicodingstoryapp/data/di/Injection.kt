package com.dicoding.dicodingstoryapp.data.di

import android.content.Context
import com.dicoding.dicodingstoryapp.data.StoryRepository
import com.dicoding.dicodingstoryapp.data.pref.UserPreferences
import com.dicoding.dicodingstoryapp.data.pref.dataStore
import com.dicoding.dicodingstoryapp.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): StoryRepository{
        val pref = providePref(context)
        val user = runBlocking { pref.getTokenUser().first() }
        if (user.isNullOrEmpty()){
            throw Exception("User not found")
        }
        val apiService = ApiConfig.getApiService(user)
        return StoryRepository.getInstance(apiService)
    }

    fun providePref(context: Context): UserPreferences{
        return UserPreferences.getInstance(context.dataStore)
    }
}