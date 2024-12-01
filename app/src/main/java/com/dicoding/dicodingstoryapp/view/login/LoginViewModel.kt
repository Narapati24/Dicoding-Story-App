package com.dicoding.dicodingstoryapp.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingstoryapp.data.pref.UserPreferences
import com.dicoding.dicodingstoryapp.data.response.LoginResponse
import com.dicoding.dicodingstoryapp.data.retrofit.ApiConfig
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val pref: UserPreferences): ViewModel() {
    private val _loginSuccess = MutableLiveData<Boolean?>()
    val loginSuccess: LiveData<Boolean?> = _loginSuccess

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    var errorMassage: String? = null

    suspend fun login(email: String, password: String){
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val apiService = ApiConfig.getApiService()
                val successResponse = apiService.login(email, password).loginResult
                pref.saveTokenUser(successResponse?.token!!)
                _loginSuccess.postValue(true)
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                errorMassage = Gson().fromJson(jsonInString, LoginResponse::class.java).message
                _loginSuccess.postValue(false)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun resetLoginStatus(){
        _loginSuccess.value = null
    }
}