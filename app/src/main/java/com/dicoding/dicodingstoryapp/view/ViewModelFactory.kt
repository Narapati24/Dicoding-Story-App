package com.dicoding.dicodingstoryapp.view

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.dicodingstoryapp.data.StoryRepository
import com.dicoding.dicodingstoryapp.data.di.Injection
import com.dicoding.dicodingstoryapp.data.pref.UserPreferences
import com.dicoding.dicodingstoryapp.view.crud.AddStoryViewModel
import com.dicoding.dicodingstoryapp.view.detail.DetailViewModel
import com.dicoding.dicodingstoryapp.view.home.HomeViewModel
import com.dicoding.dicodingstoryapp.view.login.LoginViewModel
import com.dicoding.dicodingstoryapp.view.maps.MapsViewModel

class ViewModelFactory private constructor(
    private val mApplicantion: Application
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(mApplicantion, Injection.providePref(mApplicantion)) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(mApplicantion, Injection.providePref(mApplicantion), Injection.provideRepository(mApplicantion)) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(mApplicantion, Injection.provideRepository(mApplicantion)) as T
        } else if (modelClass.isAssignableFrom(AddStoryViewModel::class.java)){
            return AddStoryViewModel(mApplicantion, Injection.provideRepository(mApplicantion)) as T
        } else if (modelClass.isAssignableFrom(MapsViewModel::class.java)){
            return MapsViewModel(mApplicantion, Injection.provideRepository(mApplicantion)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(mApplicantion: Application): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(
                    mApplicantion = mApplicantion
                )
            }.also { instance = it }
    }
}