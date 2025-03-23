package com.example.architectcoders.usecases

import com.example.architectcoders.data.SymbolsRepository

class ToggleFavoriteUseCase(private val symbolsRepository: SymbolsRepository){
    suspend operator fun invoke(symbol:String){
        symbolsRepository.toggleFavorite(symbol)
    }
}