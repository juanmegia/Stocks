import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architectcoders.domain.Result
import com.example.architectcoders.domain.StockDetail
import com.example.architectcoders.domain.ifSuccess
import com.example.architectcoders.domain.stateAsResultIn
import com.example.architectcoders.ui.detail.fetchStockProfileUseCase
import com.example.architectcoders.ui.detail.toggleFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

sealed interface DetailAction {
    data object FavoriteClick : DetailAction
}

class DetailViewModel : ViewModel() {

    private val _state = MutableStateFlow<Result<StockDetail>>(Result.Loading)
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



