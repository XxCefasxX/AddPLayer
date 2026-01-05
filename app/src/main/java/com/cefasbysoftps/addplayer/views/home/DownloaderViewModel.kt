import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import android.content.Context
import android.os.Environment
import okhttp3.ConnectionSpec

class DownloaderViewModel : ViewModel() {

    fun downloadVideo(url: String, fileName: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val BuildConfig = true
//                val client = OkHttpClient.Builder()
//                    .connectionSpecs(
//                        listOf(
//                            ConnectionSpec.MODERN_TLS,
//                            ConnectionSpec.COMPATIBLE_TLS
//                        )
//                    )
//                    .build()


                val client = if (BuildConfig) {
                    UnsafeOkHttpClient.getUnsafeOkHttpClient()
                } else {
                    OkHttpClient.Builder()
                        .connectionSpecs(
                            listOf(
                                ConnectionSpec.MODERN_TLS,
                                ConnectionSpec.COMPATIBLE_TLS
                            )
                        )
                        .build()
                }

                val request = Request.Builder()
                    .url(url)
                    .header(
                        "User-Agent",
                        "Mozilla/5.0 (Android) ExoPlayer"
                    )
                    .build()

                client.newCall(request).execute().use { response ->

                    if (!response.isSuccessful) {
                        println("HTTP error: ${response.code}")
                        return@launch
                    }

                    val moviesDir = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES)
                        ?: run {
                            println("Movies dir is null")
                            return@launch
                        }

                    val file = File(moviesDir, fileName)

                    response.body?.byteStream()?.use { input ->
                        FileOutputStream(file).use { output ->
                            input.copyTo(output)
                        }
                    } ?: run {
                        println("Response body null")
                        return@launch
                    }

                    println("Video descargado en: ${file.absolutePath}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
