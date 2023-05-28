package com.example.storyapp.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.storyapp.databinding.ActivityDetailBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val description = intent.getStringExtra("description")
        val createdAt = intent.getStringExtra("createdAt")
        val photoUrl = intent.getStringExtra("photoUrl")


        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US) // Adjust the input format based on your actual date format
        val outputFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US) // Desired output format

        try {
            val date = inputFormat.parse(createdAt)
            val formattedDate = outputFormat.format(date)
            binding.tvDate.text = formattedDate
        } catch (e: ParseException) {
            e.printStackTrace()
            binding.tvDate.text = createdAt // In case of an error, display the original createdAt value as fallback
        }

        binding.tvName.text = name
        binding.tvDesc.text = description
        Glide.with(this)
            .load(photoUrl)
            .into(binding.ivPost)
    }
}