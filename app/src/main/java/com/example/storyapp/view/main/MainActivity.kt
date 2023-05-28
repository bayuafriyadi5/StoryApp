package com.example.storyapp.view.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storyapp.R
import com.example.storyapp.data.local.room.StoryEntity
import com.example.storyapp.databinding.ActivityMainBinding
import com.example.storyapp.view.adapter.LoadingStateAdapter
import com.example.storyapp.view.adapter.StoriesAdapter
import com.example.storyapp.view.login.LoginActivity
import com.example.storyapp.view.maps.MapsActivity
import com.example.storyapp.view.post.PostActivity
import dagger.hilt.android.AndroidEntryPoint

import java.util.*
import kotlin.concurrent.schedule

@AndroidEntryPoint
@ExperimentalPagingApi
class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var lastVisiblePosition: Int = 0
    private var token: String = "null"

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mainViewModel: MainViewModel by viewModels ()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.refreshPage.setOnRefreshListener { refreshPage() }

        binding.addStory.setOnClickListener {
            val intent = Intent(this,PostActivity::class.java)
            startActivity(intent)
        }
        binding.rvStories.layoutManager = LinearLayoutManager(this)
        mainViewModel.checkIfTokenAvailable().observe(this) { token ->
            this.token = token ?: "null"

        }
        
//        setSupportActionBar(binding.toolbarMain)
//        supportActionBar?.setDisplayShowTitleEnabled(false)

        getStories()

    }

    override fun onBackPressed() {
        finishAffinity()
    }

    override fun onResume() {
        super.onResume()
        checkSession()
        if (::recyclerView.isInitialized && lastVisiblePosition != RecyclerView.NO_POSITION) {
            recyclerView.scrollToPosition(lastVisiblePosition)
        }
    }

    override fun onPause() {
        super.onPause()
        if (::recyclerView.isInitialized) {
            lastVisiblePosition =
                (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        }
    }

    private fun checkSession() {
        mainViewModel.checkIfTokenAvailable().observe(this) {
            if (it == "null") {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }
    }

    private fun getStories() {
        mainViewModel.getStories(token).observe(this) {
            val adapter = StoriesAdapter()
            binding.rvStories.adapter = adapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    adapter.retry()
                }
            )
            mainViewModel.getStories(token).observe(this) {
                adapter.submitData(lifecycle, it)
            }

        }
    }
    private fun refreshPage() {
        binding.refreshPage.isRefreshing = true
        Timer().schedule(1000) {
            binding.refreshPage.isRefreshing = false
        }
        binding.rvStories.smoothScrollBy(0, 0)
        startActivity(Intent(this, MainActivity::class.java))
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.change_language -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }

            R.id.map -> {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.logout -> {
                AlertDialog.Builder(this).apply {
                    setTitle("Logout!")
                    setMessage("Are you sure want to logout?")
                    setPositiveButton("Yes") { _, _ ->
                        mainViewModel.logout()
                        startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                        finish()
                    }
                    setNegativeButton("no"){ dialog, _ ->
                        dialog.dismiss()
                    }
                    create()
                    show()
                }

                true
            }

            else -> {
                super.onOptionsItemSelected(item)}
        }
    }

}