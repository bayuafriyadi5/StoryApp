package com.example.storyapp.di

import com.example.storyapp.data.local.room.StoryDao
import com.example.storyapp.data.local.room.StoryDatabase
import com.example.storyapp.data.remote.retrofit.ApiConfig
import com.example.storyapp.data.remote.retrofit.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideApiService(): ApiService = ApiConfig.getApiService()

    @Provides
    fun provideStoryDao(storyDatabase: StoryDatabase): StoryDao = storyDatabase.storyDao()
}