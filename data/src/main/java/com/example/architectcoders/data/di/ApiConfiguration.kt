package com.example.architectcoders.data.di

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiConfig @Inject constructor(
    val apiKey: String
)

