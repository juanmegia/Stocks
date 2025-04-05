package com.example.architectcoders.usecases

import com.example.architectcoders.domain.repository.SymbolsRepository
import com.example.architectcoders.domain.StockDetail
import kotlinx.coroutines.flow.Flow

class FetchStockProfileUseCase(private val symbolsRepository: SymbolsRepository) {
    suspend operator fun invoke(symbol: String): Flow<StockDetail?> = symbolsRepository.fetchStockProfile(symbol)
}