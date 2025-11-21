import android.R
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlayerViewModel : ViewModel() {

    private val _videoPath = MutableStateFlow<String?>(null)
    val videoPath = _videoPath.asStateFlow()

    fun loadDummyVideo() {
        _videoPath.value =  "/sdcard/Android/data/com.cefasbysoftps.addplayer/files/Movies/demo.mp4"
    }
}
