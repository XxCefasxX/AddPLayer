import android.app.Application
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.cefasbysoftps.addplayer.core.datastore.AppDatabase
import com.cefasbysoftps.addplayer.core.datastore.ReportEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val database = Room.databaseBuilder(
        application.applicationContext,
        AppDatabase::class.java,
        "adplayer_db"
    )
        .build()

    //
    private val reportDao = database.reportDao()


    private val repository = ReportRepositoryImpl(reportDao, ApiClient.reportApi)
    private val reportUseCase = ReportUseCase(repository)


    fun sendReport(userId: Int, fecha: String, tiempo: Int) {
        viewModelScope.launch {
            var result = reportUseCase(userId, fecha, tiempo)

            result.onSuccess { response ->

            }
        }
    }

    private val _reportsState = MutableStateFlow<List<ReportEntity>>(emptyList())
    val reportsState: StateFlow<List<ReportEntity>> = _reportsState

    private val _accumulatedTimeMs = MutableStateFlow<Long>(0)
    val accumulatedTimeMs: StateFlow<Long> = _accumulatedTimeMs


    fun savePlayback(
        userId: String,
        secondsPlayed: Long
    ) {
        viewModelScope.launch {
            val fecha = System.currentTimeMillis()
            reportDao.insert(
                ReportEntity(
                    userId = userId, secondsPlayed = secondsPlayed,
                    date = fecha,
                    startPlay = System.currentTimeMillis(),
                    endPlay = System.currentTimeMillis()
                )
            )
        }
    }

    fun todayEpoch(): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }

    fun loadReports() {
        viewModelScope.launch {
            val reports = reportDao.getAll()
            _reportsState.value = reports
            // Log.d("data", "Directo DAO: $reports")
        }
    }

    fun loadUserReports(userId: Int) {
        viewModelScope.launch {
            val reports = reportUseCase.loadUserLocalReports(userId)
            _reportsState.value = reports
            //  Log.d("data", "reportes del usuario $userId: $reports")
        }
    }

    fun loadTodayReports(userId: Int, date: Long) {
        viewModelScope.launch {
            val reports = reportUseCase.LoadLocalToday(userId, date)
            _accumulatedTimeMs.value = reportUseCase.todayTime(userId, date)
            _reportsState.value = reports
            //Log.d("data", "reportes del usuario $userId: $reports")
        }
    }
}