package com.example.storyapp.data.local.room


import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [StoryEntity::class, RemoteKeys::class],
    version = 3,
    exportSchema = false
)
abstract class StoryDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao

}