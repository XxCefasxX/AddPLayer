import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel(
    private val sessionDataStore: SessionDataStore
) : ViewModel() {

    val isLoggedIn = sessionDataStore.isLoggedIn

    fun logout() {
        viewModelScope.launch {
            sessionDataStore.clearSession()
        }
    }
}
