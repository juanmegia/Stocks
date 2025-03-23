package com.example.architectcoders.domain



data class Stock(
    val symbol: String,
    val name: String,
    val lastSale: String,
    val netChange: String,
    val pctChange: String,
    val marketCap: String,
    val isFavorite: Boolean = false
)
