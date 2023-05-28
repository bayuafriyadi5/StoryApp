package com.example.storyapp.view.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.ExperimentalPagingApi
import com.example.storyapp.data.local.datastore.AuthPreferences
import com.example.storyapp.data.remote.response.StoryUploadResponse
import com.example.storyapp.data.repository.StoryRepository
import com.example.storyapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class PostViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    fun postStory(
        token: String,
        imageFile: File,
        description: String,
        lat: RequestBody?,
        lon: RequestBody?
    ): LiveData<Result<StoryUploadResponse>> =
    storyRepository.postStory(token, imageFile, description, lat, lon)

    fun checkIfTokenAvailable(): LiveData<String?> = userRepository.getToken()

}