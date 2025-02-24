package com.example.architectcoders.data.datasource

import com.example.architectcoders.data.Stock
import com.example.architectcoders.data.StockDetail
import com.example.architectcoders.data.datasource.database.SymbolsDao

class SymbolsLocalDataSource(private val symbolsDao: SymbolsDao) {

    val stocks = symbolsDao.fetchPopularStocks()
    fun getStockProfile(symbol: String) = symbolsDao.fetchStockProfile(symbol)
    suspend fun isEmpty() = symbolsDao.countStocks() <= 0
    suspend fun insertStocks(stocks: List<Stock>) = symbolsDao.saveStocks(stocks)
    suspend fun insertStockDetail(stock: StockDetail) = symbolsDao.saveStockDetail(stock)
    suspend fun toggleFavorite(symbol: String) {
        symbolsDao.toggleFavorite(symbol)
        symbolsDao.toggleFavoriteProfile(symbol)
    }
}