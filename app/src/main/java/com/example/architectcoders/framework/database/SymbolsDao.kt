package com.example.architectcoders.framework.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.architectcoders.domain.Stock
import kotlinx.coroutines.flow.Flow
@Dao
interface SymbolsDao {

    @Query("SELECT * FROM StockDao")
    fun fetchPopularStocks(): Flow<List<Stock>>

    @Query("SELECT * FROM StockDetailDao WHERE companySymbol = :symbol")
    fun fetchStockProfile(symbol: String): Flow<StockDetailDao?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveStocks(stocks: List<StockDao>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveStockDetail(stockDetail: StockDetailDao)

    @Query("UPDATE StockDao SET isFavorite = NOT isFavorite WHERE symbol = :symbol")
    suspend fun toggleFavorite(symbol: String)

    @Query("UPDATE StockDetailDao SET isFavorite = NOT isFavorite WHERE companySymbol = :symbol")
    suspend fun toggleFavoriteProfile(symbol: String)
}

