package com.example.architectcoders.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.architectcoders.data.Stock
import com.example.architectcoders.data.StockDetail
import com.example.architectcoders.data.datasource.remote.Converters

@Database(entities = [Stock::class, StockDetail::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)  // Registrar el type converter
abstract class SymbolsDatabase: RoomDatabase() {
    abstract fun symbolsDao(): SymbolsDao
}