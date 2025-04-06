package com.example.architectcoders.di

import com.example.architectcoders.BuildConfig
import com.example.architectcoders.data.di.ApiConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideApiConfig(): ApiConfig = ApiConfig(BuildConfig.YAHOO_FINANCE_API_KEY)
} 