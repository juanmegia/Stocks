package com.example.architectcoders.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architectcoders.domain.Stock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.architectcoders.Result
import com.example.architectcoders.stateAsResultIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import com.example.architectcoders.usecases.FetchStocksUseCase
import com.example.architectcoders.usecases.ToggleFavoriteUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchStocksUseCase: FetchStocksUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<Result<List<Stock>>>(
        Result.Loading)
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
