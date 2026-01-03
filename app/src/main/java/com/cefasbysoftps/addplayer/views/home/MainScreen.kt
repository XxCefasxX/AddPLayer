import android.app.Application
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun MainScreen(navController: NavController, viewModel: DownloaderViewModel = viewModel(),) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val sessionDataStore = remember {
        SessionDataStore(context)
    }

    val userId by sessionDataStore.userId.collectAsState(initial = null)
    val lgviewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(sessionDataStore)
    )

    val loggedOut by lgviewModel.loggedOut.collectAsState()
    LaunchedEffect(loggedOut) {
        if (loggedOut) {
            navController.navigate("login") {
                popUpTo("main") { inclusive = true }
            }
        }
    }







    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) { // Texto con tiempo acumulado

        // Instanciamos el PlayerViewModel para obtener el tiempo acumulado
        userId?.let { id ->
            val playerViewModel: PlayerViewModel = viewModel(
                factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                        if (modelClass.isAssignableFrom(PlayerViewModel::class.java)) {
                            @Suppress("UNCHECKED_CAST")
                            return PlayerViewModel(
                                context.applicationContext as Application,
                                id
                            ) as T
                        }
                        throw IllegalArgumentException("Unknown ViewModel class")
                    }
                }
            )
            Text(
                text = "Tiempo reproducido hoy: ${playerViewModel.accumulatedTimeMs / 1000 / 60} min",
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
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

        Button(  modifier = Modifier.padding(top = 16.dp),
            onClick = {
                navController.navigate("history")
            }) {
            Text("Historial")
        }

        Button(  modifier = Modifier.padding(top = 16.dp),
            onClick = {
                lgviewModel.logout()
            }) {
            Text("Salir")
        }
    }
}
