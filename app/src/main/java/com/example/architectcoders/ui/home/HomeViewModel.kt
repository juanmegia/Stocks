package com.example.architectcoders.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architectcoders.domain.Stock
import com.example.architectcoders.ui.detail.repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.architectcoders.domain.Result
import com.example.architectcoders.domain.stateAsResultIn
import kotlinx.coroutines.flow.*


class HomeViewModel : ViewModel() {

    private val _state = MutableStateFlow<Result<List<Stock>>>(Result.Loading)
    val state: StateFlow<Result<List<Stock>>> = _state.asStateFlow()

    fun onUiReady() {
        println("onUiReady")
        viewModelScope.launch {
            repository.stocks.stateAsResultIn(viewModelScope)
                .collectLatest { result ->
                    _state.value = result
                    println("onUiReady updated: $result")
                }
        }
    }

    fun onFavoriteClick(symbol: String) {
        viewModelScope.launch {
            repository.toggleFavorite(symbol)
        }
    }

    fun onClear() {
        viewModelScope.launch {
            _state.value = Result.Success(emptyList())
        }
    }
}
