

import android.R
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Environment
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.core.content.edit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.application
import androidx.room.Room
import com.cefasbysoftps.addplayer.core.datastore.AppDatabase
import com.cefasbysoftps.addplayer.core.datastore.ReportEntity
import kotlinx.coroutines.flow.StateFlow
import java.io.File

class PlayerViewModel(
    application: Application,
    private val userId: Int
) : AndroidViewModel(application) {

    private val _videoPath = MutableStateFlow<String?>(null)
    val videoPath = _videoPath.asStateFlow()


    private var trackingJob: Job? = null
    private val prefs: SharedPreferences =
        application.getSharedPreferences("video_time", Context.MODE_PRIVATE)

    private val dateFormat = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())

    val currentDate: String
        get() = dateFormat.format(System.currentTimeMillis())

    private fun todayKey(): String {
        val today = dateFormat.format(System.currentTimeMillis())
        return "time_${userId}_$today"
    }


    private val _reportsState = MutableStateFlow<List<ReportEntity>>(emptyList())
    val reportsState: StateFlow<List<ReportEntity>> = _reportsState



    var accumulatedTimeMs: Long
        get() {
            val lastDay = prefs.getString("last_day", "") ?: ""
            val today = currentDate

            if (lastDay != today) {
                prefs.edit()
                    .putString("last_day", today)
                    .putLong("accumulated_time", 0L)
                    .apply()
            }
            return prefs.getLong("accumulated_time", 0L)
        }
        private set(value) {
            prefs.edit { putLong("accumulated_time", value) }
        }



    fun loadDummyVideo(context: Context) {
        val file = File(
            context.getExternalFilesDir(Environment.DIRECTORY_MOVIES),
            "demo.mp4"
        )

        if (file.exists()) {
            _videoPath.value = file.absolutePath
        } else {
            _videoPath.value = null
        }
    }


    // Inicia el contador
    fun startTracking() {
        trackingJob?.cancel()
        trackingJob = viewModelScope.launch {
            while (true) {
                accumulatedTimeMs += 1000L // sumar 1 segundo
                delay(1000L)
            }
        }
    }

    // Pausa el contador
    fun stopTracking() {
        trackingJob?.cancel()
    }




    private val database = Room.databaseBuilder(
        application.applicationContext,
        AppDatabase::class.java,
        "adplayer_db"
    ).build()
//
    private val reportDao = database.reportDao()

    fun savePlayback(
        userId: String,
        secondsPlayed: Long
    ) {
        viewModelScope.launch {
            reportDao.insert(
                ReportEntity(userId= userId, secondsPlayed =  secondsPlayed
                )
            )
        }
    }

    fun loadReports(){
        viewModelScope.launch {
            val reports = reportDao.getAll()
            _reportsState.value = reports
            Log.d("data", "Directo DAO: $reports")
        }
    }

    fun loadUserReports(userId: Int){
        viewModelScope.launch {
            val reports = reportDao.getByUser(userId)
            _reportsState.value = reports
            Log.d("data", "reportes del usuario $userId: $reports")
        }
    }
}
