package com.example.architectcoders.data.di

import android.app.Application
import androidx.room.Room
import com.example.architectcoders.data.database.local.SymbolsDao
import com.example.architectcoders.data.database.local.SymbolsDatabase
import com.example.architectcoders.data.database.local.SymbolsLocalDataSource
import com.example.architectcoders.data.database.remote.SymbolsClient
import com.example.architectcoders.data.database.remote.SymbolsRemoteDataSource
import com.example.architectcoders.domain.datasource.LocalDataSource
import com.example.architectcoders.domain.datasource.RemoteDataSource
import com.example.architectcoders.domain.repository.SymbolsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    
    @Provides
    @Singleton
    internal fun provideDatabase(app: Application): SymbolsDatabase {
        return Room.databaseBuilder(
            app,
            SymbolsDatabase::class.java,
            "symbols-db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideSymbolsClient(apiConfig: ApiConfig): SymbolsClient =
        SymbolsClient.create(apiConfig.apiKey)

    @Provides
    @Singleton
    internal fun provideSymbolsDao(database: SymbolsDatabase): SymbolsDao = database.symbolsDao()

    @Provides
    @Singleton
    fun provideLocalDataSource(symbolsDao: SymbolsDao): LocalDataSource = 
        SymbolsLocalDataSource(symbolsDao)

    @Provides
    @Singleton
    fun provideRemoteDataSource(symbolsClient: SymbolsClient): RemoteDataSource = 
        SymbolsRemoteDataSource(symbolsClient)

    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): SymbolsRepository = SymbolsRepository(remoteDataSource, localDataSource)
}

