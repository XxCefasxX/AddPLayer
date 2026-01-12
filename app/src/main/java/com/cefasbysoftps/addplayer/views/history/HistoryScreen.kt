import android.app.Application
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.media3.common.MediaItem
import java.io.File

@Composable
fun HistoryScreen(
    navController: NavController
) {
    val context = LocalContext.current

    val sessionDataStore = remember {
        SessionDataStore(context)
    }

    val viewModel: HistoryViewModel = viewModel( )

    val userId by sessionDataStore.userId.collectAsState(initial = null)
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

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
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.DarkGray, // Color de fondo
                        contentColor = Color.White  // Color del texto/icono
                    ),
                    onClick = {
                        var tiempo = playerViewModel.accumulatedTimeMs / 1000 / 60
                        val date = playerViewModel.currentDate
                        viewModel.sendReport(id, date, tiempo.toInt())
                    }) {
                    Text(
                        "Enviar",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }


        }
        val reports by playerViewModel.reportsState.collectAsState()

        LaunchedEffect(Unit) {
            userId?.let { id->
                playerViewModel.loadUserReports(id)
            }

        }
        LazyColumn {
            items(reports) { report ->
                Text(report.secondsPlayed.toString())
            }
        }

    }
}