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

class ViewModelFactory private constructor(
    private val pref: UserPreferences? = null,
    private val storyRepository: StoryRepository? = null
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(pref!!) as T
        }
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(storyRepository!!) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstanceForLogin(pref: UserPreferences): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(pref =  pref)
            }.also { instance = it }

        fun getInstanceForHome(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(storyRepository =  Injection.provideRepository(context))
            }.also { instance = it }
        }

    }
}