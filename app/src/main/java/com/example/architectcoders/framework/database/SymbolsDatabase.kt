package com.example.architectcoders.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.example.architectcoders.framework.remote.Converters

@Database(entities = [StockDao::class, StockDetailDao::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class SymbolsDatabase: RoomDatabase() {
    abstract fun symbolsDao(): SymbolsDao
}