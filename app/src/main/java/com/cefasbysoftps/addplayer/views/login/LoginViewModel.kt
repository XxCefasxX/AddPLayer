import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(  private val sessionDataStore: SessionDataStore) : ViewModel() {

    private val repo = LoginRepositoryImpl(FakeUserLocalDataSource())
    private val loginUseCase = LoginUseCase(repo)

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess = _loginSuccess.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun login(username: String, password: String) {
        val user = loginUseCase(username, password)

        if (user != null) {
            viewModelScope.launch {
                sessionDataStore.setLoggedIn(true)
            }
            _loginSuccess.value = true
        } else {
            _error.value = "Usuario o contraseña incorrectos"
        }
    }
}
