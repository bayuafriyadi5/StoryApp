package com.example.storyapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.storyapp.data.local.datastore.AuthPreferences
import com.example.storyapp.data.repository.StoryRepository
import com.example.storyapp.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import com.example.storyapp.data.local.room.StoryEntity
import org.mockito.stubbing.OngoingStubbing
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.storyapp.utils.*
import com.example.storyapp.view.adapter.StoriesAdapter
import kotlinx.coroutines.test.*
import org.mockito.Mock
import org.mockito.Mockito


@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var authPreferences: AuthPreferences

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() {
        mainViewModel = MainViewModel(storyRepository,authPreferences,userRepository)
    }

    private val dummyTokenData = DataDummy.DUMMY_TOKEN
    private val dummyStoriesData = DataDummy.generateDummyStories()

    private val updateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

    @Test
    fun `when successfully get all stories`() = runTest {
        val data = PagingDataSourceTest.snapshot(dummyStoriesData)
        val listStories = MutableLiveData<PagingData<StoryEntity>>()
        listStories.value = data
        `when`(storyRepository.getStories(dummyTokenData)).thenReturn(listStories)

        val stories = mainViewModel.getStories(dummyTokenData).getOrAwaitValue()
        Mockito.verify(storyRepository).getStories(dummyTokenData)

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoriesAdapter.DIFF_CALLBACK,
            updateCallback = updateCallback,
            mainDispatcher = mainDispatcherRule.testDispatcher,
            workerDispatcher = mainDispatcherRule.testDispatcher
        )
        differ.submitData(stories)
        advanceUntilIdle()

        assertNotNull(differ.snapshot())
        assertEquals(dummyStoriesData.size, differ.snapshot().size)
        assertEquals(dummyStoriesData.first(), differ.snapshot().first())
    }

    @Test
    fun `when there is no stories data`() = runTest {
        val nullList = emptyList<StoryEntity>()
        val nullData = PagingDataSourceTest.snapshot(nullList)

        val listStories = MutableLiveData<PagingData<StoryEntity>>()
        listStories.value = nullData
        `when`(storyRepository.getStories(dummyTokenData)).thenReturn(listStories)

        val stories = mainViewModel.getStories(dummyTokenData).getOrAwaitValue()

        Mockito.verify(storyRepository).getStories(dummyTokenData)

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoriesAdapter.DIFF_CALLBACK,
            updateCallback = updateCallback,
            mainDispatcher = mainDispatcherRule.testDispatcher,
            workerDispatcher = mainDispatcherRule.testDispatcher
        )
        differ.submitData(stories)
        advanceUntilIdle()

        assertEquals(0, differ.snapshot().size)
    }

}

