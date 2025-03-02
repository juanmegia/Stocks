package com.example.architectcoders.domain

interface RemoteDataSource {
    suspend fun fetchPopularStocks(): List<Stock>

    suspend fun fetchStockProfile(symbol: String): StockDetail
}