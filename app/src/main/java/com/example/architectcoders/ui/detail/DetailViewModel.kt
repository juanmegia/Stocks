import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architectcoders.App
import com.example.architectcoders.data.StockDetail
import com.example.architectcoders.data.datasource.remote.SymbolsClient
import com.example.architectcoders.data.SymbolsRepository
import com.example.architectcoders.data.datasource.SymbolsLocalDataSource
import com.example.architectcoders.data.datasource.SymbolsRemoteDataSource
import com.example.architectcoders.data.datasource.database.SymbolsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface DetailAction {
    data object FavoriteClick : DetailAction
}
class DetailViewModel : ViewModel() {
    private var state = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = state.asStateFlow()
    private val symbolsDao: SymbolsDao = App.instance.db.symbolsDao()
    private val localDataSource = SymbolsLocalDataSource(symbolsDao)
    private val repository = SymbolsRepository( SymbolsRemoteDataSource(SymbolsClient.instance), localDataSource)


    fun onUiReady(symbol: String) {
        viewModelScope.launch(Dispatchers.Default) {
            state.update { it.copy(loading = true) }
            repository.fetchStockProfile(symbol).collect{
                profile ->  state.update { currentState -> currentState.copy(profile = profile)}
            }
        }
    }
    fun onAction(action:DetailAction) {
        when (action){
            is DetailAction.FavoriteClick -> onFavoriteClick()
            else -> {}
        }
    }
    private fun onFavoriteClick() {
        state.value.profile?.let {
            viewModelScope.launch {
                repository.toggleFavorite(it.companySymbol)
            }
        }
    }

    data class UiState(
        val profile: StockDetail? = null,
        val loading: Boolean = false
    )
}
