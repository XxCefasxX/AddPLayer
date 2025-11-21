import android.app.Application
import android.media.browse.MediaBrowser
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.exoplayer.ExoPlayer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.ui.PlayerView





@Composable
fun PlayerScreen(

)
 {

     val context = LocalContext.current

     // Instanciamos el AndroidViewModel usando ViewModelProvider.Factory
     val viewModel: PlayerViewModel = viewModel(
         factory = object : androidx.lifecycle.ViewModelProvider.Factory {
             override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                 if (modelClass.isAssignableFrom(PlayerViewModel::class.java)) {
                     @Suppress("UNCHECKED_CAST")
                     return PlayerViewModel(context.applicationContext as Application) as T
                 }
                 throw IllegalArgumentException("Unknown ViewModel class")
             }
         }
     )


    viewModel.loadDummyVideo()
    val videoPath by viewModel.videoPath.collectAsState()


     val exoPlayer = remember {
         ExoPlayer.Builder(context).build().apply {
             repeatMode = ExoPlayer.REPEAT_MODE_ONE
         }
     }

    LaunchedEffect(videoPath) {
        videoPath?.let { path ->
            viewModel.startTracking()
            val mediaItem = MediaItem.fromUri(path)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.play()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopTracking()
            exoPlayer.release()
        }
    }

     val lifecycleOwner = LocalLifecycleOwner.current
     DisposableEffect(lifecycleOwner) {
         val observer = LifecycleEventObserver { _, event ->
             when (event) {
                 Lifecycle.Event.ON_PAUSE -> {
                     exoPlayer.playWhenReady = false
                 }

                 Lifecycle.Event.ON_RESUME -> {
                     exoPlayer.playWhenReady = true
                 }

                 else -> {}
             }
         }

         lifecycleOwner.lifecycle.addObserver(observer)

         onDispose {
             lifecycleOwner.lifecycle.removeObserver(observer)
         }
     }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                useController = false // Para ocultar controles
            }
        }
    )
}
