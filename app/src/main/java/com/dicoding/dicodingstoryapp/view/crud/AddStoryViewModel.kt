package com.dicoding.dicodingstoryapp.view.crud

import android.app.Application
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingstoryapp.data.Result
import com.dicoding.dicodingstoryapp.data.StoryRepository
import com.dicoding.dicodingstoryapp.data.response.AddStoryResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val mApplication: Application, private val storyRepository: StoryRepository): ViewModel() {
    private val _currentImageUri = MutableLiveData<Uri?>()
    val currentImageUri: LiveData<Uri?> get() = _currentImageUri

    fun setCurrentImageUri(uri: Uri?) {
        _currentImageUri.value = uri
    }

    private var _status = MutableLiveData<Result<AddStoryResponse>>()
    val status: LiveData<Result<AddStoryResponse>> = _status

    fun addStory(imageFile: MultipartBody.Part, description: RequestBody) {
        viewModelScope.launch {
            _status.value = storyRepository.addStory(description, imageFile)
        }
    }
}