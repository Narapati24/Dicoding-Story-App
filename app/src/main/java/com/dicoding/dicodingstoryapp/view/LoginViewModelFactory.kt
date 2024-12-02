package com.dicoding.dicodingstoryapp.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.dicodingstoryapp.data.di.Injection
import com.dicoding.dicodingstoryapp.data.pref.UserPreferences
import com.dicoding.dicodingstoryapp.view.login.LoginViewModel

class LoginViewModelFactory private constructor(
        private val pref: UserPreferences
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
                return LoginViewModel(pref) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }

        companion object{
            @Volatile
            private var instance: LoginViewModelFactory? = null

            fun getInstance(context: Context): LoginViewModelFactory =
                instance ?: synchronized(this){
                    instance ?: LoginViewModelFactory(
                        pref =  Injection.providePref(context)
                    )
                }.also { instance = it }
        }
    }