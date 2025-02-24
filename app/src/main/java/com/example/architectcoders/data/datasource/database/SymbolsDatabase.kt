package com.example.architectcoders.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.architectcoders.data.Stock
import com.example.architectcoders.data.StockDetail

@Database(entities = [Stock::class, StockDetail::class], version = 1, exportSchema = false)
abstract class SymbolsDatabase: RoomDatabase() {
    abstract fun symbolsDao(): SymbolsDao
}