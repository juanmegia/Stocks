package com.example.architectcoders.ui.detail

import com.example.architectcoders.data.repository
import com.example.architectcoders.usecases.FetchStockProfileUseCase
import com.example.architectcoders.usecases.FetchStocksUseCase
import com.example.architectcoders.usecases.ToggleFavoriteUseCase


val fetchStocksUseCase = FetchStocksUseCase(repository)
val toggleFavoriteUseCase = ToggleFavoriteUseCase(repository)
val fetchStockProfileUseCase = FetchStockProfileUseCase(repository)