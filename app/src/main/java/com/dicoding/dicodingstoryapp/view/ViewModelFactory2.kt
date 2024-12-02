package com.dicoding.dicodingstoryapp.view

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.dicodingstoryapp.data.StoryRepository
import com.dicoding.dicodingstoryapp.data.di.Injection
import com.dicoding.dicodingstoryapp.data.pref.UserPreferences
import com.dicoding.dicodingstoryapp.view.home.HomeViewModel
import com.dicoding.dicodingstoryapp.view.login.LoginViewModel

class ViewModelFactory2 private constructor(
    private val storyRepository: StoryRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(storyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory2? = null

        fun getInstanceForHome(context: Context): ViewModelFactory2 {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory2(storyRepository =  Injection.provideRepository(context))
            }.also { instance = it }
        }

    }
}