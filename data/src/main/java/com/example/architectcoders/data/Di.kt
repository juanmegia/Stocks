package com.example.architectcoders.data

import androidx.room.Room
import com.example.architectcoders.data.database.local.SymbolsDao
import com.example.architectcoders.data.database.local.SymbolsDatabase
import com.example.architectcoders.data.database.local.SymbolsLocalDataSource
import com.example.architectcoders.domain.datasource.LocalDataSource
import com.example.architectcoders.domain.datasource.RemoteDataSource
import com.example.architectcoders.domain.repository.SymbolsRepository

val db = Room.databaseBuilder(Initializer.app, SymbolsDatabase::class.java, "symbols-db") .fallbackToDestructiveMigration().build()
private val symbolsDao: SymbolsDao = db.symbolsDao()
private val localDataSource: LocalDataSource = SymbolsLocalDataSource(symbolsDao)
private val remoteDataSource: RemoteDataSource =
    com.example.architectcoders.data.database.remote.SymbolsRemoteDataSource(com.example.architectcoders.data.database.remote.SymbolsClient.instance)
val repository =
    SymbolsRepository(remoteDataSource, localDataSource)
