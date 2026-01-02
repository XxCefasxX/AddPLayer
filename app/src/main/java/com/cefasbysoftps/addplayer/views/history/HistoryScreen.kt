import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

    Button(modifier = Modifier.padding(top = 16.dp),onClick = {
        viewModel.sendReport(5,"2024-01-15",10)
    }) {
        Text("Enviar")
    }
}