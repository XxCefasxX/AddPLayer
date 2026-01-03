import android.R
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.core.content.edit

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


    private fun todayKey(): String {
        val today = dateFormat.format(System.currentTimeMillis())
        return "time_${userId}_$today"
    }


    var accumulatedTimeMs: Long
        get() = prefs.getLong(todayKey(), 0L)
        private set(tiempo) {
            prefs.edit { putLong(todayKey(), tiempo) }
        }

    fun loadDummyVideo() {
        _videoPath.value = "/sdcard/Android/data/com.cefasbysoftps.addplayer/files/Movies/demo.mp4"
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
}
