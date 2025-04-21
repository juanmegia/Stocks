package com.example.architectcoders.di

import com.example.architectcoders.data.di.DataModule
import com.example.architectcoders.usecases.FetchStockProfileUseCase
import com.example.architectcoders.usecases.ToggleFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import org.mockito.kotlin.mock
import com.example.architectcoders.domain.repository.SymbolsRepository
import com.example.architectcoders.usecases.FetchStocksUseCase

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [UseCaseModule::class, DataModule::class]
)
@Module
object TestModule {

    @Provides
    fun provideSymbolsRepository(): SymbolsRepository = mock()

    @Provides
    fun provideFetchStocksUseCase(
        repository: SymbolsRepository
    ): FetchStocksUseCase = FetchStocksUseCase(repository)

    @Provides
    fun provideFetchStockProfileUseCase(
        repository: SymbolsRepository
    ): FetchStockProfileUseCase = FetchStockProfileUseCase(repository)

    @Provides
    fun provideToggleFavoriteUseCase(
        repository: SymbolsRepository
    ): ToggleFavoriteUseCase = ToggleFavoriteUseCase(repository)
}

