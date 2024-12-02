package com.dicoding.dicodingstoryapp.view.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingstoryapp.data.response.RegisterResponse
import com.dicoding.dicodingstoryapp.data.retrofit.ApiConfig
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel: ViewModel() {
    private val _registerSuccess = MutableLiveData<Boolean?>()
    val registerSuccess: LiveData<Boolean?> = _registerSuccess


    var errorResponse: String? = null

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val apiService = ApiConfig.getApiService()
                apiService.register(name, email, password)
                _registerSuccess.postValue(true)
            } catch (e: HttpException){
                val errorBody = e.response()?.errorBody()?.string()
                errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java).message
                _registerSuccess.postValue(false)
            }
        }
    }

    fun resetRegisterStatus(){
        _registerSuccess.value = null
    }
}