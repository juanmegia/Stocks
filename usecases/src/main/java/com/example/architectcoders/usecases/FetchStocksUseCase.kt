package com.example.architectcoders.usecases

import com.example.architectcoders.data.SymbolsRepository
import com.example.architectcoders.domain.Stock
import kotlinx.coroutines.flow.Flow

class FetchStocksUseCase(private val symbolsRepository: SymbolsRepository) {
    operator fun invoke(): Flow<List<Stock>> = symbolsRepository.stocks

}