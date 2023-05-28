package com.example.storyapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.storyapp.data.remote.response.LoginResponse
import com.example.storyapp.data.remote.response.RegisterResponse
import com.example.storyapp.data.remote.retrofit.ApiService
import com.example.storyapp.data.local.datastore.AuthPreferences
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class UserRepository  @Inject constructor(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences
) {

    suspend fun login(
        email: String,
        password: String
    ): LiveData<Result<LoginResponse>> = liveData {
        try {
            val response = apiService.login(email, password)
            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            val errorMessage = when (e) {
                is HttpException -> {
                    when (e.code()) {
                        401 -> "Invalid email or password"
                        404 -> "User not found"
                        else -> "Login failed, please try again later."
                    }
                }
                is SocketTimeoutException -> "Connection timed out, please try again later."
                else -> "Login failed, please try again later."
            }
            emit(Result.failure(Throwable(errorMessage, e)))
        }
    }

    suspend fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        try {
            val response = apiService.register(name, email, password)
            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            val errorMessage = when (e) {
                is HttpException -> {
                    when (e.code()) {
                        400 -> "Email already taken"
                        else -> "Register failed, please try again later."
                    }
                }
                is SocketTimeoutException -> "Connection timed out, please try again later."
                else -> "Register failed, please try again later."
            }
            emit(Result.failure(Throwable(errorMessage, e)))
        }
    }

    fun getToken(): LiveData<String?> = authPreferences.getToken()
}