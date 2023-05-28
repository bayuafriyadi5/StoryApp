package com.example.storyapp.view.main

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.data.local.datastore.AuthPreferences
import com.example.storyapp.data.local.room.StoryEntity
import com.example.storyapp.data.repository.StoryRepository
import com.example.storyapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class MainViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    private val authPreferences: AuthPreferences,
    private val userRepository: UserRepository
) : ViewModel() {

    fun getStories(token: String): LiveData<PagingData<StoryEntity>> =
        storyRepository.getStories(token).cachedIn(viewModelScope)


    fun checkIfTokenAvailable(): LiveData<String?> = userRepository.getToken()

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            authPreferences.deleteToken()
        }
    }

}

