package com.example.architectcoders.data.datasource

import com.example.architectcoders.domain.Stock
import com.example.architectcoders.domain.StockDetail

interface RemoteDataSource {
    suspend fun fetchPopularStocks(): List<Stock>

    suspend fun fetchStockProfile(symbol: String): StockDetail
}