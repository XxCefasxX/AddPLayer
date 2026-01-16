import android.app.Application
import android.net.Uri
import android.util.Log
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import java.io.File
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(
    navController: NavController
) {
    val context = LocalContext.current

    val sessionDataStore = remember {
        SessionDataStore(context)
    }

    val viewModel: HistoryViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return HistoryViewModel(
                        context.applicationContext as Application
                    ) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    )
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
        val accumulatedTime by viewModel.accumulatedTimeMs.collectAsStateWithLifecycle()
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
                        text = "${accumulatedTime / 1000 / 60} min",
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
                        var tiempo = accumulatedTime / 1000 / 60
                        val date = playerViewModel.currentDate
                        viewModel.sendReport(id, date, tiempo.toInt())
                    }) {
                    Text(
                        "Enviar",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                val reports by viewModel.reportsState.collectAsState()

                LaunchedEffect(Unit) {
                    val today = viewModel.todayEpoch()
                    Log.d("History", "Fecha hoy $today")
                    viewModel.loadTodayReports(id, today)
                    //viewModel.loadUserReports(id)
                }
                LazyColumn {
                    items(reports) { report ->
                        Log.d("History", "Fecha reporte ${report.date}")

                        val formatterDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val date = formatterDate.format(Date(report.date))


                        val minutes = report.secondsPlayed / 1000 / 60

                        Text("Fecha:$date")
                        Text("Tiempo: $minutes")
                        Button(
                            onClick = {

                                viewModel.sendReport(id, date, minutes.toInt())
                            }
                        ) {
                            Text("Enviar")
                        }
                    }
                }
            }


        }


    }
}