import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.runtime.getValue

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SessionViewModel = viewModel(
        factory = SessionViewModelFactory(LocalContext.current)
    )
) {
    val isLogged by viewModel.isLoggedIn.collectAsState()

    LaunchedEffect(isLogged) {
        when (isLogged) {
            null -> {} // Cargando todavía
            true -> navController.navigate("main") {
                popUpTo("splash") { inclusive = true }
            }
            false -> navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    // Puedes dejar esta pantalla en blanco o con un logo
}
