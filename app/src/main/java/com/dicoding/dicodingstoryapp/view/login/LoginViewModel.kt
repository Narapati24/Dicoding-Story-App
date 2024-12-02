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

    var errorMassage: String? = null

    fun login(email: String, password: String){
        viewModelScope.launch {
            try {
                val apiService = ApiConfig.getApiService()
                val successResponse = apiService.login(email, password).loginResult
                pref.saveTokenUser(successResponse?.token!!)
                _loginSuccess.postValue(true)
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                errorMassage = Gson().fromJson(jsonInString, LoginResponse::class.java).message
                _loginSuccess.postValue(false)
            }
        }
    }

    fun resetLoginStatus(){
        _loginSuccess.value = null
    }
}