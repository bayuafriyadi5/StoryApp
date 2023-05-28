package com.example.storyapp.utils

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.storyapp.data.local.room.StoryEntity

class PagingDataSourceTest : PagingSource<Int, LiveData<List<StoryEntity>>>() {
    override fun getRefreshKey(state: PagingState<Int, LiveData<List<StoryEntity>>>): Int = 0

    override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, LiveData<List<StoryEntity>>> =
        PagingSource.LoadResult.Page(emptyList(), 0, 1)

    companion object {
        fun snapshot(stories: List<StoryEntity>) = PagingData.from(stories)
    }
}