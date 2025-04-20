package com.example.architectcoders.di

import com.example.architectcoders.usecases.FetchStockProfileUseCase
import com.example.architectcoders.usecases.ToggleFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import org.mockito.kotlin.mock

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [UseCaseModule::class]
)
object TestModule {

    @Provides
    fun provideFetchStockProfileUseCase(): FetchStockProfileUseCase = mock()

    @Provides
    fun provideToggleFavoriteUseCase(): ToggleFavoriteUseCase = mock()
} 