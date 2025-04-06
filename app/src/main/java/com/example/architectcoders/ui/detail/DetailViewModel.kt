package com.example.architectcoders.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architectcoders.Result
import com.example.architectcoders.domain.StockDetail
import com.example.architectcoders.ifSuccess
import com.example.architectcoders.stateAsResultIn
import com.example.architectcoders.usecases.FetchStockProfileUseCase
import com.example.architectcoders.usecases.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface DetailAction {
    data object FavoriteClick : DetailAction
}

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val fetchStockProfileUseCase: FetchStockProfileUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<Result<StockDetail>>(
        Result.Loading)
    val uiState: StateFlow<Result<StockDetail>> = _state.asStateFlow()

    fun onUiReady(symbol: String) {
        viewModelScope.launch(Dispatchers.IO) {
            fetchStockProfileUseCase(symbol)
                .stateAsResultIn(viewModelScope)
                .collectLatest { result ->
                    _state.value = result as Result<StockDetail>
                    println("onUiReady updated: $result")
                }
        }
    }

    fun onAction(action: DetailAction) {
        if (action is DetailAction.FavoriteClick) {
            onFavoriteClick()
        }
    }

    private fun onFavoriteClick() {
        _state.value.ifSuccess { stockDetail ->
            viewModelScope.launch { toggleFavoriteUseCase(stockDetail.companySymbol) }
        }
    }
}



