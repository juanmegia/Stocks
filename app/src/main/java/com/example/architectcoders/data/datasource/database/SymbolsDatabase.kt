package com.example.architectcoders.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.example.architectcoders.data.datasource.remote.Converters

@Database(entities = [StockDao::class, StockDetailDao::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class SymbolsDatabase: RoomDatabase() {
    abstract fun symbolsDao(): SymbolsDao
}