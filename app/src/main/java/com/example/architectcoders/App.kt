package com.example.architectcoders

import android.app.Application
import com.example.architectcoders.data.Initializer


class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Initializer.app = this
        Initializer.apiKey = BuildConfig.YAHOO_FINANCE_API_KEY
    }
}