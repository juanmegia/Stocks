package com.example.architectcoders.domain.datasource

import com.example.architectcoders.domain.Stock
import com.example.architectcoders.domain.StockDetail
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    val stocks: Flow<List<Stock>>
    fun getStockProfile(symbol: String): Flow<StockDetail?>

    suspend fun insertStocks(stocks: List<Stock>)

    suspend fun insertStockDetail(stock: StockDetail)

    suspend fun toggleFavorite(symbol: String)
}