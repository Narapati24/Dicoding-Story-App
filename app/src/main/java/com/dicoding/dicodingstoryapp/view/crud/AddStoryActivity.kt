package com.dicoding.dicodingstoryapp.view.crud

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dicoding.dicodingstoryapp.data.Result
import com.dicoding.dicodingstoryapp.databinding.ActivityAddStoryBinding
import com.dicoding.dicodingstoryapp.helper.getImageUri
import com.dicoding.dicodingstoryapp.helper.reduceFileImage
import com.dicoding.dicodingstoryapp.helper.uriToFile
import com.dicoding.dicodingstoryapp.view.ViewModelFactory
import com.dicoding.dicodingstoryapp.view.home.HomeActivity
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AddStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoryBinding
    private lateinit var viewModel: AddStoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        )[AddStoryViewModel::class.java]

        viewModel.status.observe(this) { result ->
            hideLoading()
            when (result) {
                is Result.Error -> {
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
                Result.Loading -> {
                    //
                }
                is Result.Success -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this@AddStoryActivity as Activity).toBundle())
                }
            }
        }

        binding.buttonAdd.isEnabled = false

        viewModel.currentImageUri.observe(this) { uri ->
            if (uri != null) {
                binding.buttonAdd.isEnabled = true
                showImage()
            }
        }
        showImage()
        setupAction()
    }

    private fun setupAction(){
        binding.buttonGallery.setOnClickListener {
            launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.buttonCamera.setOnClickListener {
            viewModel.setCurrentImageUri(getImageUri(this))
            launcherIntentCamera.launch(viewModel.currentImageUri.value!!)
        }

        binding.buttonAdd.setOnClickListener {
            showLoading()
            viewModel.currentImageUri.value?.let { uri->
                val imageFile = uriToFile(uri, this).reduceFileImage()
                Log.d("Image File", "showImage: ${imageFile.path}")
                val description = binding.edAddDescription.text.toString()

                val requestbody = description.toRequestBody("text/plain".toMediaType())
                val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
                val multipartBody = MultipartBody.Part.createFormData(
                    "photo",
                    imageFile.name,
                    requestImageFile
                )
                lifecycleScope.launch { 
                    viewModel.addStory(
                        multipartBody,
                        requestbody
                    )
                }
            }
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        } else {
            viewModel.setCurrentImageUri(null)
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ){ uri: Uri? ->
        if (uri != null){
            viewModel.setCurrentImageUri(uri)
            showImage()
        } else{
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        viewModel.currentImageUri.value?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivPreview.setImageURI(it)
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.buttonGallery.isEnabled = false
        binding.buttonAdd.isEnabled = false
        binding.buttonCamera.isEnabled = false
    }

    private fun hideLoading(){
        binding.progressBar.visibility = View.GONE
        binding.buttonAdd.isEnabled = true
        binding.buttonGallery.isEnabled = true
        binding.buttonCamera.isEnabled = true
    }
}