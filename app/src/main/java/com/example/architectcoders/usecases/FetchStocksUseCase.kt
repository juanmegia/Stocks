package com.example.architectcoders.usecases

import com.example.architectcoders.domain.Stock
import com.example.architectcoders.domain.SymbolsRepository
import kotlinx.coroutines.flow.Flow

class FetchStocksUseCase(private val symbolsRepository: SymbolsRepository) {
    operator fun invoke(): Flow<List<Stock>> = symbolsRepository.stocks

}