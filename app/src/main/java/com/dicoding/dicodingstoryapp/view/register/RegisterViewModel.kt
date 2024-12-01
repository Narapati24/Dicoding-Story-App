package com.dicoding.dicodingstoryapp.view.register

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingstoryapp.data.response.RegisterResponse
import com.dicoding.dicodingstoryapp.data.retrofit.ApiConfig
import com.dicoding.dicodingstoryapp.view.login.LoginActivity
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel: ViewModel() {
    private val _registerSuccess = MutableLiveData<Boolean?>()
    val registerSuccess: LiveData<Boolean?> = _registerSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    var errorResponse: String? = null

    suspend fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val apiService = ApiConfig.getApiService()
                val successResponse = apiService.register(name, email, password)
                _registerSuccess.postValue(true)
            } catch (e: HttpException){
                val errorBody = e.response()?.errorBody()?.string()
                errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java).message
                _registerSuccess.postValue(false)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun resetRegisterStatus(){
        _registerSuccess.value = null
    }
}