import android.app.Application
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun HistoryScreen(
    navController: NavController
){
    val context = LocalContext.current

    val sessionDataStore = remember {
        SessionDataStore(context)
    }

    val viewModel: HistoryViewModel = viewModel(
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
        Button(modifier = Modifier.padding(top = 16.dp), onClick = {
            var tiempo=playerViewModel.accumulatedTimeMs / 1000 / 60
            val date = playerViewModel.currentDate
            viewModel.sendReport(id, date, tiempo.toInt())
        }) {
            Text("Enviar")
        }
    }
}