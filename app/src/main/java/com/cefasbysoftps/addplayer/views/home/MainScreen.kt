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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Composable
fun MainScreen(navController: NavController, viewModel: DownloaderViewModel = viewModel()) {
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

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // ===== HEADER =====
            Text(
                text = "Panel principal",
                style = MaterialTheme.typography.headlineMedium
            )
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


                //Body

                // ===== TIEMPO =====
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Tiempo reproducido hoy",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "${playerViewModel.accumulatedTimeMs / 1000 / 60} min",
                            style = MaterialTheme.typography.displaySmall
                        )
                    }
                }


                // ===== BOTONES =====

                //Play Video
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    onClick = {
                        navController.navigate("player")
                    }
                ) {
                    Text(
                        text = "Reproducir video",
                        style = MaterialTheme.typography.titleLarge
                    )
                }


                //Download video
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    onClick = {
                        scope.launch {
                            viewModel.downloadVideo(
                                url = "https://cdn.soft-ps.com/addplayer/demo.mp4",
                                fileName = "demo.mp4",
                                context = context
                            )
                        }
                    }
                ) {
                    Text(
                        text = "Descargar video",
                        style = MaterialTheme.typography.titleLarge
                    )
                }


                //Historial
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray, // Color de fondo
                        contentColor = Color.White  // Color del texto/icono
                    ),
                    onClick = {
                        navController.navigate("history")
                    }
                ) {
                    Text(
                        text = "Historial",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                // ===== LOGOUT =====
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    onClick = {
                        lgviewModel.logout()
                        navController.navigate("login") {
                            popUpTo("main") { inclusive = true }
                        }
                    }
                ) {
                    Text(
                        text = "Salir",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onError
                    )
                }


            }

        }
    }


}

/*
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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

        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                navController.navigate("history")
            }) {
            Text("Historial")
        }

        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                lgviewModel.logout()
            }) {
            Text("Salir")
        }
    }
}
*/