package com.example.architectcoders.framework.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StockDao(
    @PrimaryKey(autoGenerate = false)
    val symbol: String,
    val name: String,
    val lastSale: String,
    val netChange: String,
    val pctChange: String,
    val marketCap: String,
    val isFavorite: Boolean = false
)
