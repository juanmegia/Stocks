package com.example.architectcoders

import android.app.Application
import dagger.hilt.android.testing.CustomTestApplication

@CustomTestApplication(Application::class)
abstract class TestApplication : Application()
