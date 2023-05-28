package com.example.storyapp.view.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.ExperimentalPagingApi
import com.example.storyapp.data.local.datastore.AuthPreferences
import com.example.storyapp.data.remote.response.StoriesResponse
import com.example.storyapp.data.repository.StoryRepository
import com.example.storyapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class MapsViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    fun getAllStoriesLocation(token: String): LiveData<Result<StoriesResponse>> =
        storyRepository.getStoryLocation(token)

    fun checkIfTokenAvailable(): LiveData<String?> = userRepository.getToken()
}