package com.example.architectcoders.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architectcoders.App
import com.example.architectcoders.data.Stock
import com.example.architectcoders.data.datasource.remote.SymbolsClient
import com.example.architectcoders.data.SymbolsRepository
import com.example.architectcoders.data.datasource.SymbolsLocalDataSource
import com.example.architectcoders.data.datasource.SymbolsRemoteDataSource
import com.example.architectcoders.data.datasource.database.SymbolsDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()
    private val symbolsDao: SymbolsDao = App.instance.db.symbolsDao()
    private val localDataSource = SymbolsLocalDataSource(symbolsDao)
    private val repository = SymbolsRepository( SymbolsRemoteDataSource(SymbolsClient.instance ), localDataSource)

        fun onUiReady() {
            viewModelScope.launch{
                _state.update { it.copy(loading = true) }
                repository.stocks.collect{stocks ->
                    _state .update { it.copy(loading = false, stocks = stocks)}
                }

            }
        }
    fun onFavoriteClick(symbol: String) {
        state.value.stocks.let {
            viewModelScope.launch {
                repository.toggleFavorite(symbol)
            }
        }
    }
    fun onClear() {
        viewModelScope.launch {
            _state.update { it.copy(stocks = emptyList()) }
        }
    }    data class UiState(
        val stocks: List<Stock> = emptyList(),
        val loading: Boolean = false)
}