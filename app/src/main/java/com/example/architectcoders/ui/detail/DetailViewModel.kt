import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.architectcoders.data.StockDetail
import com.example.architectcoders.data.SymbolsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    private var state = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = state.asStateFlow()
    private val repository = SymbolsRepository()

    fun onUiReady(symbol: String) {
        viewModelScope.launch(Dispatchers.Default) {
            state.update { it.copy(loading = true) }
            val fetchedProfile = repository.fetchStockProfile(symbol)
            state.update { currentState -> currentState.copy(profile = fetchedProfile)}
            if (state.value.profile != null) {
                state.update { it.copy(loading = false) }
            }
        }
    }

    data class UiState(
        val profile: StockDetail? = null,
        val loading: Boolean = false
    )
}
