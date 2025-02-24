package com.example.architectcoders.data

import com.example.architectcoders.data.datasource.SymbolsLocalDataSource
import com.example.architectcoders.data.datasource.SymbolsRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform


class SymbolsRepository (private val symbolsRemoteDataSource: SymbolsRemoteDataSource, private val localDataSource: SymbolsLocalDataSource) {
    val stocks: Flow<List<Stock>> = localDataSource.stocks.transform { localStocks ->
        val stocks = localStocks.takeIf { it.isNotEmpty() } ?: symbolsRemoteDataSource.fetchPopularStocks().also { localDataSource.insertStocks(it) }
        emit(stocks)
    }

    suspend fun fetchStockProfile(symbol:String): Flow<StockDetail?> = localDataSource.getStockProfile(symbol).transform { localProfile ->
        val profile = localProfile?: symbolsRemoteDataSource.fetchStockProfile(symbol).also {
            localDataSource.insertStockDetail(it)  }
        emit(profile)
    }
    suspend fun toggleFavorite(symbol: String){
        localDataSource.toggleFavorite(symbol)
    }
}




