package com.example.storyapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.example.storyapp.data.remote.response.StoryUploadResponse
import com.example.storyapp.data.local.room.StoryRemoteMediator
import com.example.storyapp.data.local.room.StoryDatabase
import com.example.storyapp.data.local.room.StoryEntity
import com.example.storyapp.data.remote.response.StoriesResponse
import com.example.storyapp.data.remote.retrofit.ApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


@ExperimentalPagingApi
class StoryRepository @Inject constructor(
    private val apiService: ApiService,
    private val storyDatabase: StoryDatabase
)  {

    fun getStories(token: String): LiveData<PagingData<StoryEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            remoteMediator = StoryRemoteMediator(
                generateToken(token),
                apiService,
                storyDatabase
            ),
            pagingSourceFactory = {
                storyDatabase.storyDao().getStories()
            }
        ).liveData

    fun getStoryLocation(token: String): LiveData<Result<StoriesResponse>> = liveData {

            try {
                val bearerToken = generateToken(token)
                val response = apiService.getStories(bearerToken, size = 30, location = 1)
                emit(Result.success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.failure(e))
            }
    }

    fun postStory(
        token: String,
        imageFile: File,
        description: String,
        lat: RequestBody? = null,
        lon: RequestBody? = null
    ): LiveData<Result<StoryUploadResponse>> = liveData {

        val textPlainMediaType = "text/plain".toMediaType()
        val imageMediaType = "image/jpeg".toMediaTypeOrNull()

        val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            imageFile.name,
            imageFile.asRequestBody(imageMediaType)
        )
        val descriptionRequestBody = description.toRequestBody(textPlainMediaType)
        try {
            val bearerToken = generateToken(token)
            val response =
                apiService.uploadStory(bearerToken, imageMultiPart, descriptionRequestBody, lat, lon)
            emit(Result.success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }

    private fun generateToken(token: String): String = "Bearer $token"
}

