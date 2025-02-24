package com.example.architectcoders.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.architectcoders.data.Stock
import com.example.architectcoders.data.StockDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface SymbolsDao {

    @Query("SELECT * FROM Stock")
    fun fetchPopularStocks(): Flow<List<Stock>>

    @Query("SELECT * FROM StockDetail WHERE companySymbol = :symbol")
    fun fetchStockProfile(symbol: String): Flow<StockDetail?>

    @Query("SELECT COUNT(*) FROM Stock")
    suspend fun countStocks(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveStocks(stocks: List<Stock>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveStockDetail(stockDetail: StockDetail)

    @Query("UPDATE Stock SET isFavorite = NOT isFavorite WHERE symbol = :symbol")
    suspend fun toggleFavorite(symbol: String)

    @Query("UPDATE StockDetail SET isFavorite = NOT isFavorite WHERE companySymbol = :symbol")
    suspend fun toggleFavoriteProfile(symbol: String)
}