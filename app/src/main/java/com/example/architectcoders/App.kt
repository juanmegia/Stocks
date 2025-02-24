package com.example.architectcoders

import android.app.Application
import androidx.room.Room
import com.example.architectcoders.data.datasource.database.SymbolsDatabase

class App: Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    lateinit var db : SymbolsDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this, SymbolsDatabase::class.java, "symbols-db").build()
        instance = this
    }
}