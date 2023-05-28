package com.example.storyapp.utils

import com.example.storyapp.data.local.room.StoryEntity

object DataDummy {
    const val DUMMY_TOKEN = "auth_token"

    fun generateDummyStories(): List<StoryEntity> {
        val stories = arrayListOf<StoryEntity>()

        for (i in 0..9) {
            val story = StoryEntity(
                id = "story-nNMleD_ca755z549",
                name = "ujang",
                description = "anjay mabar",
                photoUrl = "https://story-api.dicoding.dev/images/stories/photos-1683540846857_l3LqDbd9.jpg",
                createdAt = "2023-05-08T10:14:06.859Z",
                lat = -6.2462387,
                lon = 106.9027798
            )

            stories.add(story)
        }

        return stories
    }
}