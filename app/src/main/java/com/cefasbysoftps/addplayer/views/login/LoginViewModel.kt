import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val sessionDataStore: SessionDataStore
) : ViewModel() {

    private val repo = LoginRepositoryImpl(ApiClient.authApi)
    private val loginUseCase = LoginUseCase(repo)

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess = _loginSuccess.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _loggedOut = MutableStateFlow(false)
    val loggedOut = _loggedOut.asStateFlow()

    fun login(email: String, password: String) {


        viewModelScope.launch {
            val result = loginUseCase(email, password)

            result
                .onSuccess {
                    sessionDataStore.setLoggedIn(true)
                    _loginSuccess.value = true
                }
                .onFailure {
                    _error.value = "Credenciales inválidas"
                }
        }
    }
    fun logout(){
        viewModelScope.launch {
            sessionDataStore.clearSession()
            _loggedOut.value = true
        }
    }
}
