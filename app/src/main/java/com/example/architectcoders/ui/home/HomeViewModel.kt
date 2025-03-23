package com.example.architectcoders.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architectcoders.domain.Stock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.architectcoders.domain.Result
import com.example.architectcoders.domain.stateAsResultIn
import com.example.architectcoders.ui.detail.fetchStocksUseCase
import com.example.architectcoders.ui.detail.toggleFavoriteUseCase
import kotlinx.coroutines.flow.*


class HomeViewModel : ViewModel() {

    private val _state = MutableStateFlow<Result<List<Stock>>>(Result.Loading)
    val state: StateFlow<Result<List<Stock>>> = _state.asStateFlow()

    fun onUiReady() {
        println("onUiReady")
        viewModelScope.launch {
            fetchStocksUseCase().stateAsResultIn(viewModelScope)
                .collectLatest { result ->
                    _state.value = result
                    println("onUiReady updated: $result")
                }
        }
    }

    fun onFavoriteClick(symbol: String) {
        viewModelScope.launch {
            toggleFavoriteUseCase(symbol)
        }
    }

    fun onClear() {
        viewModelScope.launch {
            _state.value = Result.Success(emptyList())
        }
    }
}
