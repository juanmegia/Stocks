package com.example.architectcoders.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architectcoders.data.Stock
import com.example.architectcoders.data.SymbolsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    private val repository = SymbolsRepository()

        fun onUiReady() {
            viewModelScope.launch{
                _state.update { it.copy(loading = true) }
                _state .update { it.copy(loading = false, stocks = repository.fetchPopularStocks())}
            }
        }
    fun onClear() {
        viewModelScope.launch {
            _state.update { it.copy(stocks = emptyList()) }
        }
    }    data class UiState(val stocks: List<Stock> = emptyList(),
    val loading: Boolean = false)
}