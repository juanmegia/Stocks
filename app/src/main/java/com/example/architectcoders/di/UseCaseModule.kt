package com.example.architectcoders.di

import com.example.architectcoders.domain.repository.SymbolsRepository
import com.example.architectcoders.usecases.FetchStockProfileUseCase
import com.example.architectcoders.usecases.FetchStocksUseCase
import com.example.architectcoders.usecases.ToggleFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideFetchStocksUseCase(repository: SymbolsRepository) =
        FetchStocksUseCase(repository)

    @Provides
    @Singleton
    fun provideToggleFavoriteUseCase(repository: SymbolsRepository) =
        ToggleFavoriteUseCase(repository)

    @Provides
    @Singleton
    fun provideFetchStockProfileUseCase(repository: SymbolsRepository) =
        FetchStockProfileUseCase(repository)
}