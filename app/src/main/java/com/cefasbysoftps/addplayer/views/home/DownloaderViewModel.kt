import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import android.content.Context

class DownloaderViewModel : ViewModel() {

    fun downloadVideo(url: String, fileName: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()

                if (!response.isSuccessful) {
                    println("Error al descargar video: ${response.code}")
                    return@launch
                }

                val moviesDir = File(context.getExternalFilesDir("Movies"), "")
                if (!moviesDir.exists()) moviesDir.mkdirs()

                val file = File(moviesDir, fileName)

                response.body?.byteStream()?.use { input ->
                    FileOutputStream(file).use { output ->
                        input.copyTo(output)
                    }
                } ?: run {
                    println("Body del response es null")
                    return@launch
                }

                println("Video descargado en: ${file.absolutePath}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
