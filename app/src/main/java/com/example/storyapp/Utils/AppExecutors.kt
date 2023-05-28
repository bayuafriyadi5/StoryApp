package com.example.storyapp.Utils

import java.util.concurrent.Executor
import java.util.concurrent.Executors


class AppExecutors {
    val diskIO: Executor = Executors.newSingleThreadExecutor()
}