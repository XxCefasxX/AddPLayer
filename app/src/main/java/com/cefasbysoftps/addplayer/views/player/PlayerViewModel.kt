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

class PlayerViewModel(application: Application) : AndroidViewModel(application)  {

    private val _videoPath = MutableStateFlow<String?>(null)
    val videoPath = _videoPath.asStateFlow()


    private var trackingJob: Job? = null
    private val prefs: SharedPreferences =
        application.getSharedPreferences("video_time", Context.MODE_PRIVATE)
    private val dateFormat = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())


    var accumulatedTimeMs: Long
        get() {
            val lastDay = prefs.getString("last_day", "") ?: ""
            val today = dateFormat.format(System.currentTimeMillis())
            if (lastDay != today) {
                // Día nuevo → reiniciar contador
                prefs.edit().putString("last_day", today).putLong("accumulated_time", 0L).apply()
            }
            return prefs.getLong("accumulated_time", 0L)
        }
        private set(value) = prefs.edit().putLong("accumulated_time", value).apply()

    fun loadDummyVideo() {
        _videoPath.value =  "/sdcard/Android/data/com.cefasbysoftps.addplayer/files/Movies/demo.mp4"
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
