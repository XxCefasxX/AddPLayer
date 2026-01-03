import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HistoryViewModel() : ViewModel() {

    private val repository = ReportRepositoryImpl(ApiClient.reportApi)
    private val reportUseCase = ReportUseCase(repository)
    fun sendReport(userId: Int, fecha: String, tiempo: Int) {
        viewModelScope.launch {
            var result = reportUseCase(userId,fecha,tiempo)

            result. onSuccess { response ->

            }
        }
    }
}