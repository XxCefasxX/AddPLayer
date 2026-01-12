import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.cefasbysoftps.addplayer.core.datastore.AppDatabase
import com.cefasbysoftps.addplayer.core.datastore.ReportEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _reportsState = MutableStateFlow<List<ReportEntity>>(emptyList())
    val reportsState: StateFlow<List<ReportEntity>> = _reportsState

//    private val database = Room.databaseBuilder(
//        application.applicationContext,
//        AppDatabase::class.java,
//        "adplayer_db"
//    ).build()
//    //
//    private val reportDao = database.reportDao()
//    fun loadReports(){
//        viewModelScope.launch {
//            val reports = reportDao.getAll()
//            _reportsState.value = reports
//            Log.d("data", "Directo DAO: $reports")
//        }
//    }
}