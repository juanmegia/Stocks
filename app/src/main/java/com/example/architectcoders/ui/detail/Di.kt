package com.example.architectcoders.ui.detail

import com.example.architectcoders.App
import com.example.architectcoders.domain.SymbolsRepository
import com.example.architectcoders.data.datasource.SymbolsLocalDataSource
import com.example.architectcoders.data.datasource.SymbolsRemoteDataSource
import com.example.architectcoders.data.datasource.database.SymbolsDao
import com.example.architectcoders.data.datasource.remote.SymbolsClient
import com.example.architectcoders.domain.LocalDataSource
import com.example.architectcoders.domain.RemoteDataSource
import com.example.architectcoders.usecases.FetchStockProfileUseCase
import com.example.architectcoders.usecases.FetchStocksUseCase
import com.example.architectcoders.usecases.ToggleFavoriteUseCase

private val symbolsDao: SymbolsDao = App.instance.db.symbolsDao()
private val localDataSource: LocalDataSource = SymbolsLocalDataSource(symbolsDao)
private val remoteDataSource: RemoteDataSource = SymbolsRemoteDataSource(SymbolsClient.instance)
val repository = SymbolsRepository( remoteDataSource, localDataSource)
val fetchStocksUseCase = FetchStocksUseCase(repository)
val toggleFavoriteUseCase = ToggleFavoriteUseCase(repository)
val fetchStockProfileUseCase = FetchStockProfileUseCase(repository)