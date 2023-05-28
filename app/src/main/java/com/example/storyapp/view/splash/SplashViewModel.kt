package com.example.storyapp.view.splash

import androidx.lifecycle.*
import com.example.storyapp.data.local.datastore.AuthPreferences
import com.example.storyapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authPreferences: AuthPreferences,
    private val userRepository: UserRepository
) : ViewModel() {

    fun setFirstTime(firstTime: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            authPreferences.setFirstTime(firstTime)
        }
    }

    fun checkIfTokenAvailable(): LiveData<String?> = userRepository.getToken()

}