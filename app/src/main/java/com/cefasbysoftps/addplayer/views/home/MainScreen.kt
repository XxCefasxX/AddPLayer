import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext

@Composable
fun MainScreen(navController: NavController, viewModel: DownloaderViewModel = viewModel()) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            // Navegar al PlayerScreen
            navController.navigate("player")
        }) {
            Text("Reproducir video")
        }

        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                // Debug (Para desarrollo)
                Log.d("TAG", "click")
                // Descargar video
                scope.launch {
                    viewModel.downloadVideo(
                        url = "https://cdn.soft-ps.com/addplayer/demo.mp4",
                        fileName = "demo.mp4",
                        context = context
                    )
                }
            }
        ) {
            Text("Descargar video")
        }
    }
}
