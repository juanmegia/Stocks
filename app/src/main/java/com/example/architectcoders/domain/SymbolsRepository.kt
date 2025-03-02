package com.example.architectcoders.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach


class SymbolsRepository (private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource) {
    val stocks: Flow<List<Stock>> = localDataSource.stocks.onEach { localStocks ->
        println("stocks local: $localStocks")
        if (localStocks.isEmpty()) {
            val remoteStocks = remoteDataSource.fetchPopularStocks()
            println("stocks remote: $remoteStocks")
            localDataSource.insertStocks(remoteStocks)

        }
    }

        suspend fun fetchStockProfile(symbol: String): Flow<StockDetail?> =
            localDataSource.getStockProfile(symbol).onEach { localProfile ->
                if (localProfile == null) {
                    val remoteProfile = remoteDataSource.fetchStockProfile(symbol)
                    localDataSource.insertStockDetail(remoteProfile)
                }
            }

        suspend fun toggleFavorite(symbol: String) {
            localDataSource.toggleFavorite(symbol)
        }
    }




