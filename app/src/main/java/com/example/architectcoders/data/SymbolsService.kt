package com.example.architectcoders.data

import retrofit2.http.GET
import retrofit2.http.Query

interface SymbolsService {
    @GET("v2/markets/tickers?page=1&type=STOCKS")
    suspend fun fetchPopularStocks(): RemoteResult

    @GET("v1/markets/stock/modules")
    suspend fun fetchStockDetails(
        @Query("ticker") symbol: String,
        @Query("module") module: String = "asset-profile"
    ): RemoteResultStockDetail
}